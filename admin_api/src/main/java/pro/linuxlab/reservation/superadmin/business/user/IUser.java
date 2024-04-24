package pro.linuxlab.reservation.superadmin.business.user;

import pro.linuxlab.reservation.BaseResponse;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.dto.user.ResetPasswordRequest;
import pro.linuxlab.reservation.superadmin.dto.user.UserCreateRequest;
import pro.linuxlab.reservation.superadmin.dto.user.UserUpdateRequest;

public interface IUser {
    BaseResponse getAdminUserList(String search, String site, int offset,int pageSize, String sortBy, String direction);

    BaseResponse createAdminUser(UserCreateRequest userCreateRequest);

    BaseResponse updateAdminUser(String userId, UserUpdateRequest userUpdateRequest);

    BaseResponse changeAdminUserStatus(String userId, EnumPool.SiteUserStatus siteUserStatus);

    BaseResponse resetAdminUserPassword(String userId, ResetPasswordRequest resetPasswordRequest);
}
