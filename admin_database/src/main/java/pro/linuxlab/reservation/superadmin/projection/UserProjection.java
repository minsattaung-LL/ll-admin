package pro.linuxlab.reservation.superadmin.projection;

import pro.linuxlab.reservation.superadmin.EnumPool;

import java.time.LocalDateTime;

public interface UserProjection {
    String getUser_Id();
    String getKc_User_Id();
    String getUsername();
    String getOwner();
    String getPhone_Number();
    EnumPool.UserRole getUser_Role();
    EnumPool.SiteUserStatus getStatus();
    String getDescription();
    String getSite_List();
    LocalDateTime getCreated_At();
    LocalDateTime getUpdated_At();
}
