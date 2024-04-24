package pro.linuxlab.reservation.superadmin.dto.mongo;

import lombok.*;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.dto.site.SiteRequest;
import pro.linuxlab.reservation.superadmin.dto.user.UserCreateRequest;
import pro.linuxlab.reservation.superadmin.dto.user.UserUpdateRequest;
import pro.linuxlab.reservation.superadmin.entity.LLSiteConfig;
import pro.linuxlab.reservation.superadmin.entity.LLUser;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MongoDto {
    EnumPool.MongoDataConfigFunc mongoDataConfigFunc;
    SiteRequest siteRequest;
    UserCreateRequest userCreateRequest;
    UserUpdateRequest userUpdateRequest;
    LLUser llUser;
    LLSiteConfig llSiteConfig;
}
