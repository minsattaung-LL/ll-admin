package pro.linuxlab.reservation.superadmin.jdbctemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.config.AppConfig;
import pro.linuxlab.reservation.superadmin.dto.site.SiteRequest;
import pro.linuxlab.reservation.superadmin.util.Util;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SystemJdbcServiceImp implements SystemJdbcService{
    final AppConfig appConfig;
    final Util util;

    @Override
    public void buildNewDatabase(SiteRequest request) {
        DataSource dataSource = null;
        try {
            dataSource = DataSourceBuilder.create()
                    .driverClassName(appConfig.getSystemDatasource().getDriverClassName())
                    .url(request.getDatabaseUrl())
                    .username(request.getDatabaseUser())
                    .password(request.getDatabasePassword())
                    .build();

            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.execute(String.format("CREATE DATABASE %s", request.getDatabaseName()));
        } catch (Exception e) {
            log.error("JDBC ERROR: " + e);
            throw new RuntimeException(e);
        } finally {
            if (dataSource != null) {
                try {
                    dataSource.getConnection().close();
                } catch (SQLException e) {
                    log.error("Error closing connection: " + e);
                }
            }
        }
    }

    @Override
    public String generateId(EnumPool.EntityConfig entityConfig, Map<String, String> configMap) {
        String prefix = configMap.get("prefix");
        String idName = configMap.get("idName");
        String tableName = configMap.get("table");
        long start = System.currentTimeMillis();
        DataSource dataSource = null;
        try {
            dataSource = DataSourceBuilder.create()
                    .driverClassName(appConfig.getSystemDatasource().getDriverClassName())
                    .url(appConfig.getSystemDatasource().getUrl() + "/" + appConfig.getSystemDatasource().getDatabase())
                    .username(appConfig.getSystemDatasource().getUsername())
                    .password(appConfig.getSystemDatasource().getPassword())
                    .build();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            String query = String.format("SELECT MAX(SUBSTRING(%s, LOCATE('-', %s) + 1)) FROM %s WHERE %s LIKE '%s'", idName, idName, tableName, idName, prefix + "%");
            Long idLong = jdbcTemplate.queryForObject(query, Long.class);
            log.info("query [{}], id [{}]",query, idLong);
            if (idLong == null || idLong.equals(0L)) {
                idLong = 0L;
            }
            return util.generateID(prefix, idLong);
        } catch (Exception e) {
            log.error("error while generating id: " + e);
            throw new RuntimeException();
        } finally {
            if (dataSource != null) {
                try {
                    dataSource.getConnection().close();
                } catch (SQLException e) {
                    log.error("Error closing connection: " + e);
                }
            }
            log.info("[ID Generating] : {}ms", System.currentTimeMillis() - start);
        }
    }

}
