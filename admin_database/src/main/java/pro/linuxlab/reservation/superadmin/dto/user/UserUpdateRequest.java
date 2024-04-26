package pro.linuxlab.reservation.superadmin.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pro.linuxlab.reservation.superadmin.validation.SiteUserStatusChecker;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class UserUpdateRequest {
    @NotBlank
    String owner;
    @NotEmpty
    List<String> systemList;
    @NotBlank
    String description;
    @NotBlank
    String phoneNumber;
    @SiteUserStatusChecker
    String status;
}
