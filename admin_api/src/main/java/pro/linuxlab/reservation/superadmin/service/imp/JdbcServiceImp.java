package pro.linuxlab.reservation.superadmin.service.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.config.AppConfig;
import pro.linuxlab.reservation.superadmin.dto.site.SiteRequest;
import pro.linuxlab.reservation.superadmin.service.JdbcService;
import pro.linuxlab.reservation.util.Util;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class JdbcServiceImp implements JdbcService {
    final AppConfig appConfig;
    final Util util;

    @Override
    public String generateId(EnumPool.EntityConfig entityConfig, Map<String, String> configMap) {
        String prefix = configMap.get("prefix");
        String idName = configMap.get("idName");
        String tableName = configMap.get("table");
        long start = System.currentTimeMillis();
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(
                    DataSourceBuilder.create()
                            .driverClassName(appConfig.getSystemDatasource().getDriverClassName())
                            .url(appConfig.getSystemDatasource().getUrl())
                            .username(appConfig.getSystemDatasource().getUsername())
                            .password(appConfig.getSystemDatasource().getPassword())
                            .build()
            );
            String query = String.format("SELECT MAX(SUBSTRING(%s, LOCATE('-', %s) + 1)) FROM %s WHERE %s LIKE ?", idName, idName, tableName, idName);
            Long idLong = jdbcTemplate.queryForObject(query, Long.class);
            if (idLong == null || idLong.equals(0L)) {
                idLong = 1L;
            }
            return util.generateID(prefix, idLong);
        } catch (Exception e) {
            log.error("error while generating id: " + e);
            throw new RuntimeException();
        } finally {
            log.info("[ID Generating] : {}ms", System.currentTimeMillis() - start);
        }
    }

    @Override
    public Boolean isDatabaseExists(String url, String user, String pass, String name) {
        log.info("url : [{}] | user : [{}] | pass : [{}] | name : [{}]", url, user, pass, name);
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(
                    DataSourceBuilder.create()
                            .driverClassName(appConfig.getSystemDatasource().getDriverClassName())
                            .url(url)
                            .username(user)
                            .password(pass)
                            .build()
            );
            return jdbcTemplate.queryForList("Show Databases", String.class).stream().anyMatch(x -> x.equalsIgnoreCase(name));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
