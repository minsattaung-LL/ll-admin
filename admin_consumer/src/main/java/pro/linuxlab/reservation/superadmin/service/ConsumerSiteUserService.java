package pro.linuxlab.reservation.superadmin.service;

import pro.linuxlab.reservation.superadmin.entity.LLSiteStaff;
import pro.linuxlab.reservation.superadmin.entity.LLUser;

public interface ConsumerSiteUserService {
    void save(LLSiteStaff entity);

    void deleteSiteAndUserByUserId(LLUser entity);
}
