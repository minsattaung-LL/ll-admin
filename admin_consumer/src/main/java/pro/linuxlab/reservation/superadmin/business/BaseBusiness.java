package pro.linuxlab.reservation.superadmin.business;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.jdbctemplate.SystemJdbcService;
import pro.linuxlab.reservation.superadmin.util.Util;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class BaseBusiness {

    @Autowired
    Util util;

    @Autowired
    SystemJdbcService jdbcService;

    @Getter
    private Map<EnumPool.EntityConfig, Map<String, String>> configMap;

    @PostConstruct
    public void initialize() {
        configMap = new HashMap<>();
        Map<String, String> each = new HashMap<>();
        each.put("prefix","C");
        each.put("idName","customer_id");
        each.put("table","ll_customer");
        configMap.put(EnumPool.EntityConfig.CUSTOMER, each);
        each = new HashMap<>();
        each.put("prefix","CI");
        each.put("idName","customer_inquiry_id");
        each.put("table","ll_customer_inquiry");
        configMap.put(EnumPool.EntityConfig.CUSTOMER_INQUIRY, each);
        each = new HashMap<>();
        each.put("prefix","P");
        each.put("idName","partner_with_us_id");
        each.put("table","ll_partner_with_us");
        configMap.put(EnumPool.EntityConfig.PARTNER_WITH_US, each);
        each = new HashMap<>();
        each.put("prefix","S");
        each.put("idName","site_id");
        each.put("table","ll_site_config");
        configMap.put(EnumPool.EntityConfig.SITE_CONFIG, each);
        each = new HashMap<>();
        each.put("prefix","SS");
        each.put("idName","site_staff_id");
        each.put("table","ll_site_staff");
        configMap.put(EnumPool.EntityConfig.SITE_STAFF, each);
        each = new HashMap<>();
        each.put("prefix","U");
        each.put("idName","user_id");
        each.put("table","ll_user");
        configMap.put(EnumPool.EntityConfig.USER, each);
    }
    public String generateId (EnumPool.EntityConfig entityConfig) {
        return jdbcService.generateId(entityConfig, configMap.get(entityConfig));
    }
}
