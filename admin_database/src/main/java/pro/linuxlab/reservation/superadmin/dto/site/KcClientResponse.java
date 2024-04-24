package pro.linuxlab.reservation.superadmin.dto.site;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class KcClientResponse {
    String kcClientId;
    String kcClientSecret;
    String kcRoleName;
}
