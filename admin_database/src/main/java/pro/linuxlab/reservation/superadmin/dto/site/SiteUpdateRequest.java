package pro.linuxlab.reservation.superadmin.dto.site;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

@Getter
@Setter
@RequiredArgsConstructor
public class SiteUpdateRequest {
    @NotBlank
    String siteName;
}
