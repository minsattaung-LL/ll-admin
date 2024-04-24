package pro.linuxlab.reservation.superadmin.dto.site;

import lombok.Getter;
import lombok.Setter;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.projection.SiteConfigProjection;

import java.time.LocalDateTime;

@Getter
@Setter
public class SiteResponse {
    String siteId;
    String siteName;
    String kcClientId;
    String kcClientSecret;
    String databaseUrl;
    String databaseName;
    String databaseUser;
    String databasePassword;
    String description;
    String usernameList;
    EnumPool.SiteUserStatus siteUserStatus;
    LocalDateTime createdAt;
    String createdBy;
    LocalDateTime updatedAt;
    String updatedBy;

    public SiteResponse(SiteConfigProjection projection) {
        this.siteId = projection.getSite_Id();
        this.siteName = projection.getSite_Name();
        this.kcClientId = projection.getKC_Client_Id();
        this.kcClientSecret = projection.getKC_Client_Secret();
        this.databaseUrl = projection.getDatabase_Url();
        this.databaseName = projection.getDatabase_Name();
        this.databaseUser = projection.getDatabase_User();
        this.databasePassword = projection.getDatabase_Password();
        this.description = projection.getDescription();
        this.siteUserStatus = projection.getStatus();
        this.usernameList = projection.getUsernameList();
        this.createdAt = projection.getCreated_At();
        this.createdBy = projection.getCreated_By();
        this.updatedAt = projection.getUpdated_At();
        this.updatedBy = projection.getUpdated_By();
    }
}
