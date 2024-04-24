package pro.linuxlab.reservation.superadmin.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ResetPasswordRequest {
    @NotBlank
    String password;
}
