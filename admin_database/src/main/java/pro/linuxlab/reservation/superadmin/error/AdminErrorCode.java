package pro.linuxlab.reservation.superadmin.error;

public class AdminErrorCode {
    public AdminErrorCode() {
    }
    public interface Business {
        String ENTITY_NOT_EXISTS = "00001";
        String ENTITY_EXISTS = "00002";
        String INVALID_REQUEST = "00003";
        String DATABASE_ERROR = "00004";
        String KEYCLOAK_CLIENT_ERROR = "00005";
        String KEYCLOAK_USER_ERROR = "00006";
        String KEYCLOAK_ROLE_ERROR = "00007";
        String KEYCLOAK_PASSWORD_ERROR = "00008";
        String DUPLICATE_REQUEST = "00009";
        String INVALID_STATUS = "00010";
        String BUSINESS_TYPE_NOT_EXISTS = "00010";
    }
}
