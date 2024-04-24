package pro.linuxlab.reservation.superadmin.dto.site;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pro.linuxlab.reservation.superadmin.validation.SiteUserStatusChecker;

@Getter
@Setter
@RequiredArgsConstructor
public class SiteRequest extends SiteUpdateRequest {
    @NotBlank
    String databaseUrl;
    @NotBlank
    String databaseName;
    @NotBlank
    String databaseUser;
    @NotBlank
    String databasePassword;
    String description;
    @SiteUserStatusChecker
    String status;

    public SiteRequest(SiteUpdateRequest request) {
        this.siteName = request.getSiteName();
    }
}
