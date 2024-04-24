package pro.linuxlab.reservation.superadmin.service.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pro.linuxlab.reservation.exception.BusinessException;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.config.AppConfig;
import pro.linuxlab.reservation.superadmin.dto.keycloak.ClientCreateRequest;
import pro.linuxlab.reservation.superadmin.dto.site.KcClientResponse;
import pro.linuxlab.reservation.superadmin.dto.site.SiteRequest;
import pro.linuxlab.reservation.superadmin.dto.user.UserCreateRequest;
import pro.linuxlab.reservation.superadmin.dto.user.UserUpdateRequest;
import pro.linuxlab.reservation.superadmin.entity.LLSiteConfig;
import pro.linuxlab.reservation.superadmin.entity.LLUser;
import pro.linuxlab.reservation.superadmin.service.ConsumerKeycloakService;
import pro.linuxlab.reservation.util.Util;

import java.util.*;

import static pro.linuxlab.reservation.superadmin.error.AdminErrorCode.Business.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerKeycloakServiceImp implements ConsumerKeycloakService {
    final Keycloak keycloak;
    final AppConfig appConfig;
    final Util util;

    @Override
    public String createUser(UserCreateRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        log.info("request : " + util.toJson(request));
        Map<String, Object> userMap = new HashMap<>();

        userMap.put("username", request.getUsername());
        userMap.put("enabled", true);

        List<Map<String, String>> credentials = new ArrayList<>();
        Map<String, String> credentialMap = new HashMap<>();
        credentialMap.put("type", "password");
        credentialMap.put("value", request.getPassword());
        credentials.add(credentialMap);

        userMap.put("credentials", credentials);
        String token = keycloak.tokenManager().getAccessToken().getToken();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(userMap, httpHeaders);
        try {
            ResponseEntity<Object> response = restTemplate.postForEntity(appConfig.getKeycloakCreateUserUrl(), requestEntity, Object.class);

            String[] uri = response.getHeaders().get("Location").get(0).split("/");
            String userId = uri[uri.length - 1];

            if (response.getStatusCode().is2xxSuccessful()) {
                addRoleToUser(userId, httpHeaders, restTemplate);
                log.info("User done");
                return userId;
            } else {
                throw new BusinessException(KEYCLOAK_USER_ERROR);
            }
        } catch (Exception ex) {
            log.error("error : " + ex);
            throw new BusinessException(KEYCLOAK_USER_ERROR);
        }
    }

    private void addRoleToUser(String userId, HttpHeaders httpHeaders, RestTemplate restTemplate) {
        String roleName = "ll-admin";
        log.info("AddRoleToUser : {}", userId + " " + roleName);
        String url = String.format("%s/%s/role-mappings/realm/available", appConfig.getKeycloakCreateUserUrl(), userId);
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Object.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> body;
            url = String.format("%s/%s/role-mappings/realm", appConfig.getKeycloakCreateUserUrl(), userId);
            if (response.getBody() instanceof List) {
                List<Map<String, Object>> objectList = (List<Map<String, Object>>) response.getBody();
                body = objectList.stream()
                        .filter(obj -> roleName.equals(obj.get("name"))).findFirst().orElse(null);
            } else {
                log.error("realm role response  : " + response);
                throw new BusinessException(KEYCLOAK_ROLE_ERROR);
            }
            log.info("url : [{}] | body : [{}]",url, util.toJson(body));
            requestEntity = new HttpEntity<>(Collections.singletonList(body), httpHeaders);
            log.info("request entity : [{}]", util.toJson(requestEntity));
            response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Object.class);
            log.info("post response : [{}]",util.toJson(response));
            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("client put response  : " + response);
                throw new BusinessException(KEYCLOAK_ROLE_ERROR);
            }
        } else {
            log.error("client get response  : " + response);
            throw new BusinessException(KEYCLOAK_ROLE_ERROR);
        }
    }

    @Override
    public void updateSystemAttributes(UserUpdateRequest userUpdateRequest, LLUser entity) {
        Map<String, String> systemMap = new HashMap<>();
        systemMap.put("system", String.join(",", userUpdateRequest.getSystemList()));
        updateVariableForUser(entity.getKcUserId(), "attributes", systemMap);
    }

    @Override
    public void changeUserStatus(LLUser entity, EnumPool.SiteUserStatus status) {
        switch (status) {
            case Active -> updateVariableForUser(entity.getKcUserId(), "enabled", true);
            case Block -> updateVariableForUser(entity.getKcUserId(), "enabled", false);
        }
    }

    private void updateVariableForUser(String kcId, String name, Object obj) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("%s/%s?userProfileMetadata=true", appConfig.getKeycloakCreateUserUrl(), kcId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(keycloak.tokenManager().getAccessToken().getToken());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Object.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseMap = (Map<String, Object>) response.getBody();
            responseMap.put(name, obj);
            requestEntity = new HttpEntity<>(responseMap, httpHeaders);
            response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Object.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("client put response  : " + response);
                throw new BusinessException(KEYCLOAK_USER_ERROR);
            }
        } else {
            log.error("client get response  : " + response);
            throw new BusinessException(KEYCLOAK_USER_ERROR);
        }
    }

    @Override
    public KcClientResponse createKeycloakClient(SiteRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        String clientId = String.format("%s-client", request.getSiteName());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(keycloak.tokenManager().getAccessToken().getToken());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> client = getClientByClientId(clientId, restTemplate);
        if (client != null) {
            throw new BusinessException(KEYCLOAK_CLIENT_ERROR);
        }
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("oauth2.device.authorization.grant.enabled", false);
        attributes.put("oidc.ciba.grant.enabled", false);
        ClientCreateRequest requestData = ClientCreateRequest.builder()
                .protocol("openid-connect")
                .clientId(clientId)
                .publicClient(false)
                .authorizationServicesEnabled(false)
                .serviceAccountsEnabled(false)
                .implicitFlowEnabled(false)
                .directAccessGrantsEnabled(true)
                .standardFlowEnabled(true)
                .frontchannelLogout(true)
                .attributes(attributes)
                .alwaysDisplayInConsole(false)
                .build();

        HttpEntity<ClientCreateRequest> requestEntity = new HttpEntity<>(requestData, httpHeaders);
        try {
            ResponseEntity<Object> response = restTemplate.postForEntity(appConfig.getKeycloakCreateClientUrl(), requestEntity, Object.class);
            log.info("Info : " + util.toJson(response));
            if (response.getStatusCode().is2xxSuccessful()) {
                String id = getIdForClient(clientId, restTemplate, httpHeaders);
                log.info("Client id : {}", id);
                String adminRoleName = createAdminRole(id, request.getSiteName(), restTemplate, httpHeaders);
                log.info("Admin Role id : {}", adminRoleName);
                String clientSecret = getSecretForClient(id, restTemplate, httpHeaders);
                log.info("clientSecret id : {}", clientSecret);
                return KcClientResponse.builder()
                        .kcClientId(clientId)
                        .kcClientSecret(clientSecret)
                        .kcRoleName(adminRoleName)
                        .build();
            } else {
                throw new BusinessException(KEYCLOAK_CLIENT_ERROR);
            }
        } catch (Exception ex) {
            log.error("error : " + ex);
            throw new BusinessException(KEYCLOAK_CLIENT_ERROR);
        }
    }

    @Override
    public void updateStatusOfKcClient(LLSiteConfig entity) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> client = getClientByClientId(entity.getKcClientId(), restTemplate);
        if (client == null || client.isEmpty()) {
            throw new BusinessException(KEYCLOAK_CLIENT_ERROR);
        }
        client.put("enabled", entity.getStatus().equals(EnumPool.SiteUserStatus.Active));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(keycloak.tokenManager().getAccessToken().getToken());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(client, httpHeaders);

        log.info("request : "+util.toJson(requestEntity));

        ResponseEntity<Object> response = restTemplate.exchange(
                String.format("%s/%s", appConfig.getKeycloakCreateClientUrl(), client.get("id")),
                HttpMethod.PUT,
                requestEntity,
                Object.class);
        log.info("response : "+util.toJson(response));

        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Keycloak Response : {}",util.toJson(response));
            throw new BusinessException(KEYCLOAK_USER_ERROR);
        }

    }

    private Map<String, Object> getClientByClientId(String clientId, RestTemplate restTemplate) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(keycloak.tokenManager().getAccessToken().getToken());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<Object> response = restTemplate.exchange(appConfig.getKeycloakCreateClientUrl(),
                HttpMethod.GET,
                requestEntity,
                Object.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            if (response.getBody() instanceof List) {
                List<Map<String, Object>> objectList = (List<Map<String, Object>>) response.getBody();
                return objectList.stream()
                        .filter(obj -> clientId.equals(obj.get("clientId"))).findFirst().orElse(null);
            } else {
                log.error("client response  : " + response);
                throw new BusinessException(KEYCLOAK_CLIENT_ERROR);
            }
        } else {
            log.error("client response  : " + response);
            return null;
        }
    }

    private String getSecretForClient(String id, RestTemplate restTemplate, HttpHeaders httpHeaders) throws JsonProcessingException {
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(null, httpHeaders);
        log.info("DI YOUK");
        ResponseEntity<String> secretResponse = restTemplate.exchange(String.format("%s/%s/client-secret", appConfig.getKeycloakCreateClientUrl(), id),
                HttpMethod.GET,
                requestEntity,
                String.class);
        if (secretResponse.getStatusCode().is2xxSuccessful()) {
            log.info("Secret : {}", util.toJson(secretResponse.getBody()));
            Optional<String> secret = extractSecretValue(secretResponse);
            if (secret.isEmpty()) {
                log.error("secret not found");
                throw new BusinessException(KEYCLOAK_CLIENT_ERROR);
            }
            return secret.get();
        } else {
            throw new BusinessException(KEYCLOAK_CLIENT_ERROR);
        }
    }

    private String createAdminRole(String id, String system, RestTemplate restTemplate, HttpHeaders httpHeaders) {
        String roleName = system + "-admin";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", roleName);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, httpHeaders);

        ResponseEntity<Object> response = restTemplate.exchange(String.format("%s/%s/roles", appConfig.getKeycloakCreateClientUrl(), id),
                HttpMethod.POST,
                requestEntity,
                Object.class);
        log.info("Response: " + response);
        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("admin role create fail");
            throw new BusinessException(KEYCLOAK_ROLE_ERROR);
        }
        return roleName;
    }

    private Optional<String> extractSecretValue(ResponseEntity<String> secretResponse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseBodyMap = objectMapper.readValue(secretResponse.getBody(), Map.class);
        log.info("secret response: {}", util.toJson(responseBodyMap));
        if ("secret".equals(responseBodyMap.get("type"))) {
            return Optional.ofNullable((String) responseBodyMap.get("value"));
        }
        return Optional.empty();
    }

    private String getIdForClient(String clientId, RestTemplate restTemplate, HttpHeaders httpHeaders) {
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<Object> response = restTemplate.exchange(appConfig.getKeycloakCreateClientUrl(),
                HttpMethod.GET,
                requestEntity,
                Object.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            if (response.getBody() instanceof List) {
                List<Map<String, Object>> objectList = (List<Map<String, Object>>) response.getBody();
                Optional<String> id = objectList.stream()
                        .filter(obj -> clientId.equals(obj.get("clientId")))
                        .map(obj -> obj.get("id").toString())
                        .findFirst();
                return id.orElse("");
            } else {
                log.error("client response  : " + response);
                throw new BusinessException(KEYCLOAK_CLIENT_ERROR);
            }
        } else {
            log.error("client response  : " + response);
            throw new BusinessException(KEYCLOAK_CLIENT_ERROR);
        }
    }

}
