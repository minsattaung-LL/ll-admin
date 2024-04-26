package pro.linuxlab.reservation.superadmin.business.site;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.business.BaseBusiness;
import pro.linuxlab.reservation.superadmin.dto.mongo.MongoDto;
import pro.linuxlab.reservation.superadmin.dto.site.KcClientResponse;
import pro.linuxlab.reservation.superadmin.dto.site.SiteRequest;
import pro.linuxlab.reservation.superadmin.entity.LLSiteConfig;
import pro.linuxlab.reservation.superadmin.exception.BusinessException;
import pro.linuxlab.reservation.superadmin.jdbctemplate.SystemJdbcService;
import pro.linuxlab.reservation.superadmin.service.ConsumerKeycloakService;
import pro.linuxlab.reservation.superadmin.service.ConsumerSiteService;

import static pro.linuxlab.reservation.superadmin.error.AdminErrorCode.Business.ENTITY_NOT_EXISTS;

@Component
@Slf4j
@RequiredArgsConstructor
public class ConsumerSiteBusinessImp extends BaseBusiness implements ConsumerSiteBusiness {
    final ConsumerSiteService consumerSiteService;
    final ConsumerKeycloakService consumerKeycloakService;
    final SystemJdbcService systemJdbcService;

    @Override
    public void createNewSite(MongoDto mongoDto) {
        LLSiteConfig entity = mongoDto.getLlSiteConfig();
        SiteRequest request = mongoDto.getSiteRequest();
        log.info("entity: [{}]", entity);
        log.info("request: [{}]", request);
        KcClientResponse kcClientResponse = consumerKeycloakService.createKeycloakClient(request);
        systemJdbcService.buildNewDatabase(request);
        entity.setKcClientId(kcClientResponse.getKcClientId());
        entity.setKcClientSecret(kcClientResponse.getKcClientSecret());
        consumerSiteService.save(entity);
    }

    @Override
    public void updateSite(MongoDto mongoDto) {
        LLSiteConfig entity = mongoDto.getLlSiteConfig();
        entity = consumerSiteService.findById(entity.getSiteId()).orElseThrow(()->new BusinessException(ENTITY_NOT_EXISTS));
        entity.setSiteName(mongoDto.getSiteRequest().getSiteName());
        consumerSiteService.save(entity);
    }

    @Override
    public void updateStatusOfSite(MongoDto mongoDto) {
        LLSiteConfig entity = mongoDto.getLlSiteConfig();
        entity = consumerSiteService.findById(entity.getSiteId()).orElseThrow(()->new BusinessException(ENTITY_NOT_EXISTS));
        entity.setStatus(EnumPool.SiteUserStatus.valueOf(mongoDto.getSiteRequest().getStatus()));
        consumerKeycloakService.updateStatusOfKcClient(entity);
        consumerSiteService.save(entity);
    }
}
