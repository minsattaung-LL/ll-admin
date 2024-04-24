package pro.linuxlab.reservation.superadmin.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.projection.UserProjection;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class UserResponse {
    String userId;
    String kcUserId;
    String username;
    String owner;
    String phoneNumber;
    String systemList;
    String description;
    EnumPool.SiteUserStatus siteUserStatus;
    EnumPool.UserRole userRole;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public UserResponse(UserProjection projection) {
        this.userId = projection.getUser_Id();
        this.kcUserId = projection.getKc_User_Id();
        this.owner = projection.getOwner();
        this.phoneNumber = projection.getPhone_Number();
        this.userRole = projection.getUser_Role();
        this.username = projection.getUsername();
        this.systemList = projection.getSite_List();
        this.description = projection.getDescription();
        this.siteUserStatus = projection.getStatus();
        this.createdAt = projection.getCreated_At();
        this.updatedAt = projection.getUpdated_At();
    }
}
