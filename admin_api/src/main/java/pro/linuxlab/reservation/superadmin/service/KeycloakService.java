package pro.linuxlab.reservation.superadmin.service;


public interface KeycloakService {
    void resetPassword(String password, String kcUserId);
}
