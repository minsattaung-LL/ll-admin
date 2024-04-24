package pro.linuxlab.reservation.superadmin.business.site;

import pro.linuxlab.reservation.BaseResponse;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.dto.site.SiteRequest;
import pro.linuxlab.reservation.superadmin.dto.site.SiteUpdateRequest;

public interface ISite {
    BaseResponse getSiteConfigList();

    BaseResponse getSite(String siteName, String kcClientId, String kcClientSecret, String databaseUrl, String databaseName, String databaseUser, String databasePassword, String description, String siteUserStatus, int offset, int pageSize, String sortBy, String direction);

    BaseResponse createNewSite(SiteRequest request);

    BaseResponse updateSite(String systemId, SiteUpdateRequest request);

    BaseResponse changeSiteStatus(String systemId, EnumPool.SiteUserStatus siteUserStatus);
}
