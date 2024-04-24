package pro.linuxlab.reservation.superadmin.business.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import pro.linuxlab.reservation.BaseResponse;
import pro.linuxlab.reservation.exception.BusinessException;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.business.BaseBusiness;
import pro.linuxlab.reservation.superadmin.dto.mongo.MongoDto;
import pro.linuxlab.reservation.superadmin.dto.user.ResetPasswordRequest;
import pro.linuxlab.reservation.superadmin.dto.user.UserCreateRequest;
import pro.linuxlab.reservation.superadmin.dto.user.UserUpdateRequest;
import pro.linuxlab.reservation.superadmin.entity.LLUser;
import pro.linuxlab.reservation.superadmin.queue.KafkaSender;
import pro.linuxlab.reservation.superadmin.service.KeycloakService;
import pro.linuxlab.reservation.superadmin.service.MongoService;
import pro.linuxlab.reservation.superadmin.service.UserService;
import pro.linuxlab.reservation.util.Util;

import java.util.List;

import static pro.linuxlab.reservation.superadmin.error.AdminErrorCode.Business.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserBusiness extends BaseBusiness implements IUser {
    final KeycloakService keycloakService;
    final Util util;
    final MongoService mongoService;
    final KafkaSender kafkaSender;
    final UserService userService;

    @Override
    public BaseResponse getAdminUserList(String search, String site, int offset, int pageSize, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(offset, pageSize, Sort.by(sortBy, direction));
        try {
            return util.generateDefaultResponse(userService.getUserData(search, site, pageable));
        } catch (Exception e) {
            log.error("error while getting site list: " + e);
            throw new BusinessException(DATABASE_ERROR);
        }
    }

    @Override
    public BaseResponse createAdminUser(UserCreateRequest userCreateRequest) {
        LLUser llUser = checkAndChangeRequest(userCreateRequest, null, userCreateRequest.getUsername());
        String key = generateKeyForMongo(EnumPool.KeyPrefix.USER);
        mongoService.saveInMongo(key,
                MongoDto.builder()
                        .mongoDataConfigFunc(EnumPool.MongoDataConfigFunc.CREATE)
                        .llUser(llUser)
                        .userCreateRequest(userCreateRequest)
                        .build());
        kafkaSender.sendSiteMessage(key);
        return util.generateDefaultResponse(null);
    }

    @Override
    public BaseResponse updateAdminUser(String userId, UserUpdateRequest userUpdateRequest) {
        LLUser llUser = checkAndChangeRequest(userUpdateRequest, userId, null);
        String key = generateKeyForMongo(EnumPool.KeyPrefix.USER);
        mongoService.saveInMongo(key,
                MongoDto.builder()
                        .mongoDataConfigFunc(EnumPool.MongoDataConfigFunc.UPDATE)
                        .llUser(llUser)
                        .userUpdateRequest(userUpdateRequest)
                        .build());
        kafkaSender.sendSiteMessage(key);
        return util.generateDefaultResponse(null);
    }

    @Override
    public BaseResponse changeAdminUserStatus(String userId, EnumPool.SiteUserStatus siteUserStatus) {
        LLUser llUser = (LLUser) findById(userId, EnumPool.EntityConfig.USER).orElseThrow(() -> new BusinessException(ENTITY_NOT_EXISTS));
        llUser.setStatus(siteUserStatus);
        String key = generateKeyForMongo(EnumPool.KeyPrefix.USER);
        mongoService.saveInMongo(key,
                MongoDto.builder()
                        .mongoDataConfigFunc(EnumPool.MongoDataConfigFunc.STATUS)
                        .llUser(llUser)
                        .build());
        kafkaSender.sendSiteMessage(key);
        return util.generateDefaultResponse(null);
    }

    @Override
    public BaseResponse resetAdminUserPassword(String userId, ResetPasswordRequest resetPasswordRequest) {
        LLUser entity = (LLUser) findById(userId, EnumPool.EntityConfig.USER).orElseThrow(() -> new BusinessException(ENTITY_NOT_EXISTS));
        keycloakService.resetPassword(resetPasswordRequest.getPassword(), entity.getKcUserId());
        return util.generateDefaultResponse(null);
    }

    private LLUser checkAndChangeRequest(UserUpdateRequest userUpdateRequest, String userId, String username) {
        LLUser entity = (userId == null)? new LLUser(): (LLUser) findById(userId, EnumPool.EntityConfig.USER).orElseThrow(() -> new BusinessException(ENTITY_NOT_EXISTS));
        if (StringUtils.hasLength(username)) {
            entity.setUsername(username);
            if (userService.findUserByUsername(username).isPresent()) {
                throw new BusinessException(DUPLICATE_REQUEST);
            }
        }
        List<String> systemList = getSiteList();
        for (String system : userUpdateRequest.getSystemList()) {
            if (systemList.stream().noneMatch(x -> x.equals(system))) {
                throw new BusinessException(INVALID_REQUEST);
            }
        }
        entity.setUserId((StringUtils.hasLength(userId))?userId:generateId(EnumPool.EntityConfig.USER));
        entity.setStatus(EnumPool.SiteUserStatus.Active);
        entity.setDescription(userUpdateRequest.getDescription());
        entity.setOwner(userUpdateRequest.getOwner());
        return entity;
    }
}
