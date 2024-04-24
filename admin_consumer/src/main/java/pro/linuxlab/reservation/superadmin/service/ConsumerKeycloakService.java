package pro.linuxlab.reservation.superadmin.service;

import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.dto.site.KcClientResponse;
import pro.linuxlab.reservation.superadmin.dto.site.SiteRequest;
import pro.linuxlab.reservation.superadmin.dto.user.UserCreateRequest;
import pro.linuxlab.reservation.superadmin.dto.user.UserUpdateRequest;
import pro.linuxlab.reservation.superadmin.entity.LLSiteConfig;
import pro.linuxlab.reservation.superadmin.entity.LLUser;

public interface ConsumerKeycloakService {
    String createUser(UserCreateRequest request);

    void updateSystemAttributes(UserUpdateRequest userUpdateRequest, LLUser entity);

    void changeUserStatus(LLUser entity, EnumPool.SiteUserStatus status);

    KcClientResponse createKeycloakClient(SiteRequest request);

    void updateStatusOfKcClient(LLSiteConfig entity);
}
