package pro.linuxlab.reservation.superadmin.service.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pro.linuxlab.reservation.exception.BusinessException;
import pro.linuxlab.reservation.superadmin.config.AppConfig;
import pro.linuxlab.reservation.superadmin.service.KeycloakService;
import pro.linuxlab.reservation.util.Util;

import java.util.HashMap;
import java.util.Map;

import static pro.linuxlab.reservation.superadmin.error.AdminErrorCode.Business.KEYCLOAK_PASSWORD_ERROR;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakServiceImp implements KeycloakService {
    final Keycloak keycloak;
    final AppConfig appConfig;
    final Util util;
    @Override
    public void resetPassword(String password, String userId) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("%s/%s/reset-password", appConfig.getKeycloakCreateUserUrl(), userId);

        Map<String, Object> request = new HashMap<>();
        request.put("temporary", true);
        request.put("type", "password");
        request.put("value", password);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(keycloak.tokenManager().getAccessToken().getToken());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(request, httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new BusinessException(KEYCLOAK_PASSWORD_ERROR);
        }
    }
}
