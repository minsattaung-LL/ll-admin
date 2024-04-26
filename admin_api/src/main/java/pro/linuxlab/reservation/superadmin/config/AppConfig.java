package pro.linuxlab.reservation.superadmin.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "app.config")
@Configuration
@Getter
@Setter
public class AppConfig {

    private String siteTopic;

    private String userTopic;

    private String keycloakAdminUser;

    private String keycloakAdminPassword;

    private String keycloakClient;

    private String keycloakClientSecret;

    private String keycloakRealm;

    private String keycloakCreateUserUrl;

    private String keycloakCreateClientUrl;

    private Datasource systemDatasource;

    @Data
    public static class Datasource {
        private String url;
        private String database;
        private String username;
        private String password;
        private String driverClassName;
    }
}
