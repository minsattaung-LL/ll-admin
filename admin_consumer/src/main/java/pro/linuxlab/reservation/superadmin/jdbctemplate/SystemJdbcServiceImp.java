package pro.linuxlab.reservation.superadmin.jdbctemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pro.linuxlab.reservation.superadmin.config.AppConfig;
import pro.linuxlab.reservation.superadmin.dto.site.SiteRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class SystemJdbcServiceImp implements SystemJdbcService{
    final AppConfig appConfig;

    @Override
    public void buildNewDatabase(SiteRequest request) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(
                DataSourceBuilder.create()
                        .driverClassName(appConfig.getSystemDatasource().getDriverClassName())
                        .url(request.getDatabaseUrl())
                        .username(request.getDatabaseUser())
                        .password(request.getDatabasePassword())
                        .build()
        );
        try {
            jdbcTemplate.execute(String.format("CREATE DATABASE %s", request.getDatabaseName()));
        } catch (Exception e) {
            log.error("JDBC ERROR : "+ e);
            throw new RuntimeException();
        }
    }
}
