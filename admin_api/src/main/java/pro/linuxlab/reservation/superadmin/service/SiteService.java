package pro.linuxlab.reservation.superadmin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pro.linuxlab.reservation.superadmin.entity.LLSiteConfig;
import pro.linuxlab.reservation.superadmin.projection.SiteConfigProjection;

import java.util.List;
import java.util.Optional;

public interface SiteService {
    Optional<LLSiteConfig> findById(String id);

    LLSiteConfig save(LLSiteConfig entity);

    Optional<LLSiteConfig> findLLSiteConfigBySiteName(String siteName);

    Optional<LLSiteConfig> findLLSiteConfigByKcClientId(String siteName);

    List<String> getSiteConfigList();

    Page<SiteConfigProjection> getSiteConfigData(String siteName, String kcClientId, String kcClientSecret, String databaseUrl, String databaseName, String databaseUser, String databasePassword, String description, String siteUserStatus, Pageable pageable);
}
