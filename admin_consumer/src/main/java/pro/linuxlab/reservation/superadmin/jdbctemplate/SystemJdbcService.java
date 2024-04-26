package pro.linuxlab.reservation.superadmin.jdbctemplate;

import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.dto.site.SiteRequest;

import java.util.Map;

public interface SystemJdbcService {

    void buildNewDatabase(SiteRequest request);

    String generateId(EnumPool.EntityConfig entityConfig, Map<String, String> stringStringMap);
}
