package pro.linuxlab.reservation.superadmin.business.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.business.BaseBusiness;
import pro.linuxlab.reservation.superadmin.dto.mongo.MongoDto;
import pro.linuxlab.reservation.superadmin.dto.user.UserCreateRequest;
import pro.linuxlab.reservation.superadmin.dto.user.UserUpdateRequest;
import pro.linuxlab.reservation.superadmin.entity.LLSiteConfig;
import pro.linuxlab.reservation.superadmin.entity.LLSiteStaff;
import pro.linuxlab.reservation.superadmin.entity.LLUser;
import pro.linuxlab.reservation.superadmin.exception.BusinessException;
import pro.linuxlab.reservation.superadmin.service.ConsumerKeycloakService;
import pro.linuxlab.reservation.superadmin.service.ConsumerSiteService;
import pro.linuxlab.reservation.superadmin.service.ConsumerSiteUserService;
import pro.linuxlab.reservation.superadmin.service.ConsumerUserService;
import pro.linuxlab.reservation.superadmin.util.Util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pro.linuxlab.reservation.superadmin.error.AdminErrorCode.Business.DATABASE_ERROR;
import static pro.linuxlab.reservation.superadmin.error.AdminErrorCode.Business.ENTITY_NOT_EXISTS;

@Component
@Slf4j
@RequiredArgsConstructor
public class ConsumerUserBusinessImp extends BaseBusiness implements ConsumerUserBusiness {
    final ConsumerUserService consumerUserService;
    final ConsumerSiteService consumerSiteService;
    final ConsumerSiteUserService consumerSiteUserService;
    final ConsumerKeycloakService consumerKeycloakService;
    final Util util;

    @Override
    public void createNewUser(MongoDto mongoDto) {
        LLUser entity = mongoDto.getLlUser();
        UserCreateRequest request = mongoDto.getUserCreateRequest();
        String kcUserId = consumerKeycloakService.createUser(request);
        entity.setKcUserId(kcUserId);
        log.info("entity: [{}]", util.toJson(entity));
        entity = consumerUserService.save(entity);
        saveSystemAndUser(entity, request.getSystemList());
    }

    @Override
    public void updateUser(MongoDto mongoDto) {
        LLUser entity = mongoDto.getLlUser();
        entity = consumerUserService.findById(entity.getUserId()).orElseThrow(() -> new BusinessException(ENTITY_NOT_EXISTS));
        UserUpdateRequest request = mongoDto.getUserUpdateRequest();
        consumerKeycloakService.updateSystemAttributes(request, entity);
        saveAndDeleteSystemAndUser(entity, request.getSystemList());
    }

    private void saveAndDeleteSystemAndUser(LLUser entity, List<String> systemList) {
        try {
            consumerSiteUserService.deleteSiteAndUserByUserId(entity);
            saveSystemAndUser(entity, systemList);
        } catch (Exception e) {
            log.error("Error while saving and deleting system and user config");
            throw new BusinessException(DATABASE_ERROR);
        }
    }

    @Override
    public void updateStatusOfUser(MongoDto mongoDto) {
        String id = mongoDto.getLlUser().getUserId();
        EnumPool.SiteUserStatus status = mongoDto.getLlUser().getStatus();
        LLUser entity = consumerUserService.findById(id).orElseThrow(() -> new BusinessException(ENTITY_NOT_EXISTS));
        entity.setStatus(status);
        consumerKeycloakService.changeUserStatus(entity, status);
        consumerUserService.save(entity);
    }

    private void saveSystemAndUser(LLUser llUser, List<String> systemList) {
        Map<String, LLSiteConfig> siteMap = getSystemMap();
        LLSiteStaff entity = new LLSiteStaff();
        for (String system : systemList) {
            entity.setId(generateId(EnumPool.EntityConfig.SITE_STAFF));
            entity.setLlUser(llUser);
            entity.setLlSiteConfig(siteMap.get(system));
            consumerSiteUserService.save(entity);
            entity = new LLSiteStaff();
        }
    }

    public Map<String, LLSiteConfig> getSystemMap() {
        List<LLSiteConfig> projectionList = consumerSiteService.getSiteList();
        return projectionList.stream().collect(Collectors.toMap(LLSiteConfig::getSiteName, x -> x));
    }
}
