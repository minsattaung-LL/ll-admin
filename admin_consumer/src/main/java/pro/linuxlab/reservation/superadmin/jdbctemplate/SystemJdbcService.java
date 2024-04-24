package pro.linuxlab.reservation.superadmin.jdbctemplate;

import pro.linuxlab.reservation.superadmin.dto.site.SiteRequest;

public interface SystemJdbcService {

    void buildNewDatabase(SiteRequest request);
}
