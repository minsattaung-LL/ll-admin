package pro.linuxlab.reservation.superadmin.business.site;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import pro.linuxlab.reservation.superadmin.BaseResponse;
import pro.linuxlab.reservation.superadmin.exception.BusinessException;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.business.BaseBusiness;
import pro.linuxlab.reservation.superadmin.dto.mongo.MongoDto;
import pro.linuxlab.reservation.superadmin.dto.site.SiteRequest;
import pro.linuxlab.reservation.superadmin.dto.site.SiteResponse;
import pro.linuxlab.reservation.superadmin.dto.site.SiteUpdateRequest;
import pro.linuxlab.reservation.superadmin.entity.LLSiteConfig;
import pro.linuxlab.reservation.superadmin.queue.KafkaSender;
import pro.linuxlab.reservation.superadmin.service.JdbcService;
import pro.linuxlab.reservation.superadmin.service.MongoService;
import pro.linuxlab.reservation.superadmin.service.SiteService;
import pro.linuxlab.reservation.superadmin.util.Util;

import java.util.*;

import static pro.linuxlab.reservation.superadmin.error.AdminErrorCode.Business.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class SiteBusiness extends BaseBusiness implements ISite {
    final SiteService siteService;
    final JdbcService jdbcService;
    final MongoService mongoService;
    final KafkaSender kafkaSender;
    final Util util;

    @Override
    public BaseResponse getSiteConfigList() {
        try {
            return util.generateDefaultResponse(getSiteList());
        } catch (Exception e) {
            log.error("error while getting site list: " + e);
            throw new BusinessException(DATABASE_ERROR);
        }
    }

    @Override
    public BaseResponse getSite(String siteName, String kcClientId, String kcClientSecret, String databaseUrl, String databaseName, String databaseUser, String databasePassword, String description, String siteUserStatus, int offset, int pageSize, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.valueOf(direction.toUpperCase(Locale.ROOT)), sortBy));
        try {
            return util.generateDefaultResponse(siteService.getSiteConfigData(siteName, kcClientId, kcClientSecret, databaseUrl, databaseName, databaseUser, databasePassword, description, siteUserStatus,pageable).map(SiteResponse::new));
        } catch (Exception e) {
            log.error("error while getting site data : " + e);
            throw new BusinessException(DATABASE_ERROR);
        }
    }

    @Override
    public BaseResponse createNewSite(SiteRequest request) {
        LLSiteConfig siteConfig = checkAndChangeRequest(request, "systemId");
        String key = generateKeyForMongo(EnumPool.KeyPrefix.SITE);
        mongoService.saveInMongo(key,
                MongoDto.builder()
                .mongoDataConfigFunc(EnumPool.MongoDataConfigFunc.CREATE)
                .llSiteConfig(siteConfig)
                .siteRequest(request)
                .build());
        kafkaSender.sendSiteMessage(key);
        return util.generateDefaultResponse(null);
    }

    @Override
    public BaseResponse updateSite(String systemId, SiteUpdateRequest request) {
        LLSiteConfig siteConfig = checkAndChangeRequest(new SiteRequest(request), systemId);
        String key = generateKeyForMongo(EnumPool.KeyPrefix.SITE);
        mongoService.saveInMongo(key,
                MongoDto.builder()
                        .mongoDataConfigFunc(EnumPool.MongoDataConfigFunc.UPDATE)
                        .llSiteConfig(new LLSiteConfig(systemId))
                        .siteRequest(new SiteRequest(request))
                        .build());
        kafkaSender.sendSiteMessage(key);
        return util.generateDefaultResponse(null);
    }

    @Override
    public BaseResponse changeSiteStatus(String siteId, EnumPool.SiteUserStatus siteUserStatus) {
        LLSiteConfig siteConfig = findSiteById(siteId);
        if (siteConfig.getStatus().equals(siteUserStatus)) {
            return util.generateDefaultResponse(null);
        }
        siteConfig.setStatus(siteUserStatus);
        String key = generateKeyForMongo(EnumPool.KeyPrefix.SITE);
        SiteRequest request = new SiteRequest();
        request.setStatus(siteUserStatus.name());
        mongoService.saveInMongo(key,
                MongoDto.builder()
                        .mongoDataConfigFunc(EnumPool.MongoDataConfigFunc.STATUS)
                        .llSiteConfig(new LLSiteConfig(siteId))
                        .siteRequest(request)
                        .build());
        kafkaSender.sendSiteMessage(key);
        return util.generateDefaultResponse(null);
    }

    private LLSiteConfig findSiteById(String siteId) {
        Optional<LLSiteConfig> siteConfigOptional = (Optional<LLSiteConfig>) findById(siteId, EnumPool.EntityConfig.SITE_CONFIG);
        if (siteConfigOptional.isEmpty()) throw new BusinessException(ENTITY_NOT_EXISTS);
        return siteConfigOptional.get();
    }

    private LLSiteConfig checkAndChangeRequest(SiteRequest request, String siteId) {
        Optional<LLSiteConfig> siteConfigOptional = (Optional<LLSiteConfig>) findById(siteId, EnumPool.EntityConfig.SITE_CONFIG);
        LLSiteConfig llSiteConfig;
        if (siteConfigOptional.isEmpty()) {
            checkBlankCase(request);
            llSiteConfig = new LLSiteConfig();
//            Duplicate Check
            if (siteService.findLLSiteConfigBySiteName(request.getSiteName()).isPresent()) {
                throw new BusinessException(DUPLICATE_REQUEST);
            }
//            Database Duplicate
            if (jdbcService.isDatabaseExists(request.getDatabaseUrl(), request.getDatabaseUser(), request.getDatabasePassword(), request.getDatabaseName())) {
                throw new BusinessException(DUPLICATE_REQUEST);
            }
//            Keycloak Duplicate
            List<String> siteList = new ArrayList<>(Arrays.stream(request.getSiteName().split("[^a-zA-Z0-9]")).toList());
            log.info("site List : [{}]",util.toJson(siteList));
            siteList.add("client");
            String clientId = String.join("-", siteList);
            if (siteService.findLLSiteConfigByKcClientId(clientId).isPresent()) {
                throw new BusinessException(DUPLICATE_REQUEST);
            }
            llSiteConfig.setKcClientId(clientId);
            changeCreateRequest(llSiteConfig, request);
        } else {
            llSiteConfig = siteConfigOptional.get();
            log.info("llsiteconfig: {}",llSiteConfig);
            log.info("request: {}", request);
//            Duplicate Check
            if (!llSiteConfig.getSiteName().equals(request.getSiteName()) && siteService.findLLSiteConfigBySiteName(request.getSiteName()).isPresent()) {
                throw new BusinessException(DUPLICATE_REQUEST);
            }
            llSiteConfig.setSiteName(request.getSiteName());
        }
        return llSiteConfig;
    }

    private void checkBlankCase(SiteRequest request) {
        if (StringUtils.isBlank(request.getSiteName()) ||
                StringUtils.isBlank(request.getDatabaseName()) ||
                StringUtils.isBlank(request.getDatabaseUrl()) ||
                StringUtils.isBlank(request.getDatabaseUser()) ||
                StringUtils.isBlank(request.getDatabasePassword()) ||
                StringUtils.isBlank(request.getStatus())) {
//            log.info("[{}] [{}] [{}] [{}] [{}] [{}]",request.getSiteName()
//                    ,request.getDatabaseName()
//                    ,request.getDatabaseUrl()
//                    ,request.getDatabaseUser()
//                    ,request.getDatabasePassword()
//                    ,request.getStatus());
            throw new BusinessException(INVALID_REQUEST);
        }
    }

    private void changeCreateRequest(LLSiteConfig llSiteConfig, SiteRequest request) {
        llSiteConfig.setSiteId(generateId(EnumPool.EntityConfig.SITE_CONFIG));
        llSiteConfig.setSiteName(request.getSiteName());
        llSiteConfig.setDatabaseUrl(request.getDatabaseUrl());
        llSiteConfig.setDatabaseName(request.getDatabaseName());
        llSiteConfig.setDatabaseUser(request.getDatabaseUser());
        llSiteConfig.setDatabasePassword(request.getDatabasePassword());
        llSiteConfig.setDescription(request.getDescription());
        llSiteConfig.setStatus(EnumPool.SiteUserStatus.valueOf(request.getStatus()));
    }
}
