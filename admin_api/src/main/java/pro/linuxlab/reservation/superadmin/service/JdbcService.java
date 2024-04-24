package pro.linuxlab.reservation.superadmin.service;

import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.dto.site.SiteRequest;

import java.util.Map;

public interface JdbcService {
    String generateId (EnumPool.EntityConfig entityConfig, Map<String, String> configMap);

    Boolean isDatabaseExists(String url, String user, String pass, String name);
}
