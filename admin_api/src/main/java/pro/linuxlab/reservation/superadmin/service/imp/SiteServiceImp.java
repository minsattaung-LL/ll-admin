package pro.linuxlab.reservation.superadmin.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pro.linuxlab.reservation.superadmin.entity.LLSiteConfig;
import pro.linuxlab.reservation.superadmin.projection.SiteConfigProjection;
import pro.linuxlab.reservation.superadmin.repo.LLSiteConfigRepo;
import pro.linuxlab.reservation.superadmin.service.SiteService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SiteServiceImp implements SiteService {
    final LLSiteConfigRepo llSiteConfigRepo;
    @Override
    public Optional<LLSiteConfig> findById(String id) {
        return llSiteConfigRepo.findById(id);
    }

    @Override
    public LLSiteConfig save(LLSiteConfig entity) {
        return llSiteConfigRepo.save(entity);
    }

    @Override
    public Optional<LLSiteConfig> findLLSiteConfigBySiteName(String siteName) {
        return llSiteConfigRepo.findLLSiteConfigBySiteName(siteName);
    }

    @Override
    public Optional<LLSiteConfig> findLLSiteConfigByKcClientId(String siteName) {
        return llSiteConfigRepo.findLLSiteConfigByKcClientId(siteName);
    }

    @Override
    public List<String> getSiteConfigList() {
        return llSiteConfigRepo.getSiteConfigList();
    }

    @Override
    public Page<SiteConfigProjection> getSiteConfigData(String siteName, String kcClientId, String kcClientSecret, String databaseUrl, String databaseName, String databaseUser, String databasePassword, String description, String siteUserStatus, Pageable pageable) {
        return llSiteConfigRepo.getSiteConfigData(siteName, kcClientId, kcClientSecret, databaseUrl, databaseName, databaseUser, databasePassword, description, siteUserStatus, pageable);
    }
}
