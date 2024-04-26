package pro.linuxlab.reservation.superadmin.business;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import pro.linuxlab.reservation.superadmin.BaseResponse;
import pro.linuxlab.reservation.superadmin.exception.BusinessException;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.EnumPool.EntityConfig;
import pro.linuxlab.reservation.superadmin.entity.*;
import pro.linuxlab.reservation.superadmin.service.*;
import pro.linuxlab.reservation.superadmin.util.Util;

import java.util.*;

import static pro.linuxlab.reservation.superadmin.error.AdminErrorCode.Business.DATABASE_ERROR;

@Slf4j
@Component
public class BaseBusiness {
    @Autowired
    SiteService siteService;
    @Autowired
    UserService userService;
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerInquiryService customerInquiryService;
    @Autowired
    PartnerWithUsService partnerWithUsService;
    @Autowired
    SiteStaffService siteStaffService;
    @Autowired
    JdbcService jdbcService;
    @Autowired
    Util util;
    @Getter
    private Map<EntityConfig, Map<String, String>> configMap;

    @PostConstruct
    public void initialize() {
        configMap = new HashMap<>();
        Map<String, String> each = new HashMap<>();
        each.put("prefix","C");
        each.put("idName","customer_id");
        each.put("table","ll_customer");
        configMap.put(EntityConfig.CUSTOMER, each);
        each = new HashMap<>();
        each.put("prefix","CI");
        each.put("idName","customer_inquiry_id");
        each.put("table","ll_customer_inquiry");
        configMap.put(EntityConfig.CUSTOMER_INQUIRY, each);
        each = new HashMap<>();
        each.put("prefix","P");
        each.put("idName","partner_with_us_id");
        each.put("table","ll_partner_with_us");
        configMap.put(EntityConfig.PARTNER_WITH_US, each);
        each = new HashMap<>();
        each.put("prefix","S");
        each.put("idName","site_id");
        each.put("table","ll_site_config");
        configMap.put(EntityConfig.SITE_CONFIG, each);
        each = new HashMap<>();
        each.put("prefix","SS");
        each.put("idName","site_staff_id");
        each.put("table","ll_site_staff");
        configMap.put(EntityConfig.SITE_STAFF, each);
        each = new HashMap<>();
        each.put("prefix","U");
        each.put("idName","user_id");
        each.put("table","ll_user");
        configMap.put(EntityConfig.USER, each);
    }
    public List<String> getSiteList() {
        try {
            return siteService.getSiteConfigList();
        } catch (Exception e) {
            log.error("error : " + e);
            throw new BusinessException(DATABASE_ERROR);
        }
    }
    public Optional<?> findById (String id, EntityConfig entityConfig) {
        return switch (entityConfig) {
            case CUSTOMER -> customerService.findById(id);
            case CUSTOMER_INQUIRY -> customerInquiryService.findById(id);
            case PARTNER_WITH_US -> partnerWithUsService.findById(id);
            case USER -> userService.findById(id);
            case SITE_CONFIG -> siteService.findById(id);
            case SITE_STAFF -> siteStaffService.findById(id);
        };
    }

    public Object save(Object entity, EntityConfig entityConfig) {
        return switch (entityConfig) {
            case CUSTOMER -> customerService.save((LLCustomer) entity);
            case CUSTOMER_INQUIRY -> customerInquiryService.save((LLCustomerInquiry) entity);
            case PARTNER_WITH_US -> partnerWithUsService.save((LLPartnerWithUs) entity);
            case USER -> userService.save((LLUser) entity);
            case SITE_CONFIG -> siteService.save((LLSiteConfig) entity);
            case SITE_STAFF -> siteStaffService.save((LLSiteStaff) entity);
        };
    }
    public String generateId (EntityConfig entityConfig) {
        return jdbcService.generateId(entityConfig, configMap.get(entityConfig));
    }

    public Page<?> toPage(List<?> list, int pageSize, int pageNo, String sortBy) {
        int totalPages = list.size() / pageSize;
        PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        int max = pageNo >= totalPages ? list.size() : pageSize * (pageNo + 1);
        int min = pageNo > totalPages ? max : pageSize * pageNo;
        return new PageImpl<>(list.subList(min, max), pageable, list.size());
    }
    public String generateKeyForMongo(EnumPool.KeyPrefix keyPrefix) {
        return EnumPool.KeyPrefix.getPrefix(keyPrefix) +"-" + UUID.randomUUID().toString().substring(0,8);
    }
}
