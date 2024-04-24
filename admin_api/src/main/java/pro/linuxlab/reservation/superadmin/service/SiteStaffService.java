package pro.linuxlab.reservation.superadmin.service;

import pro.linuxlab.reservation.superadmin.entity.LLSiteStaff;

import java.util.Optional;

public interface SiteStaffService {
    Optional<LLSiteStaff> findById(String id);

    LLSiteStaff save(LLSiteStaff entity);
}
