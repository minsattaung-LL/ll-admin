package pro.linuxlab.reservation.superadmin.projection;

import pro.linuxlab.reservation.superadmin.EnumPool;

import java.time.LocalDateTime;

public interface SiteConfigProjection {
    String getSite_Id();

    String getSite_Name();

    String getKC_Client_Id();

    String getKC_Client_Secret();

    String getDatabase_Url();

    String getDatabase_Name();

    String getDatabase_User();

    String getDatabase_Password();

    EnumPool.SiteUserStatus getStatus();

    String getDescription();

    String getUsernameList();

    LocalDateTime getCreated_At();

    String getCreated_By();

    LocalDateTime getUpdated_At();

    String getUpdated_By();
}
