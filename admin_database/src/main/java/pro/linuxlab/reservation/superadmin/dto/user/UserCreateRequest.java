package pro.linuxlab.reservation.superadmin.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserCreateRequest extends UserUpdateRequest {
    @NotBlank
    String username;
    @NotBlank
    String password;
}
