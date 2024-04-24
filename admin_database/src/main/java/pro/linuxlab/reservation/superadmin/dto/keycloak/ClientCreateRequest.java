package pro.linuxlab.reservation.superadmin.dto.keycloak;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class ClientCreateRequest {
    String protocol;
    String clientId;
    String name;
    String description;
    Boolean publicClient;
    Boolean authorizationServicesEnabled;
    Boolean serviceAccountsEnabled;
    Boolean implicitFlowEnabled;
    Boolean directAccessGrantsEnabled;
    Boolean standardFlowEnabled;
    Boolean frontchannelLogout;
    Map<String, Object> attributes;
    Boolean alwaysDisplayInConsole;
    String rootUrl;
    String baseUrl;
}
