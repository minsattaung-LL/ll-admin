package pro.linuxlab.reservation.superadmin.service;

import pro.linuxlab.reservation.superadmin.entity.LLSiteConfig;

import java.util.List;
import java.util.Optional;

public interface ConsumerSiteService {
    List<LLSiteConfig> getSiteList();

    void save(LLSiteConfig entity);

    Optional<LLSiteConfig> findById(String siteId);
}
