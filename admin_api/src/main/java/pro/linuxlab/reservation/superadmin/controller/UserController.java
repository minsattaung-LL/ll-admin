package pro.linuxlab.reservation.superadmin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.business.user.IUser;
import pro.linuxlab.reservation.superadmin.dto.user.ResetPasswordRequest;
import pro.linuxlab.reservation.superadmin.dto.user.UserCreateRequest;
import pro.linuxlab.reservation.superadmin.dto.user.UserUpdateRequest;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    final IUser iUser;
    @GetMapping("/get")
    public ResponseEntity<?> getAdminUserList(@RequestParam(required = false) String search,
                                              @RequestParam(required = false) String site,
                                              @RequestParam(name = "offset", defaultValue = "0", required = false) int offset,
                                              @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
                                              @RequestParam(name = "sortBy", defaultValue = "created_at", required = false) String sortBy,
                                              @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(iUser.getAdminUserList(search, site, offset, pageSize, sortBy, direction));
    }
    @PostMapping("/create")
    public ResponseEntity<?> createAdminUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        return ResponseEntity.ok(iUser.createAdminUser(userCreateRequest));
    }
    @PutMapping("/{userId}/update")
    public ResponseEntity<?> updateAdminUser(@PathVariable String userId ,@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.ok(iUser.updateAdminUser(userId, userUpdateRequest));
    }
    @PutMapping("/{userId}/reset-password")
    public ResponseEntity<?> resetAdminUserPassword(@PathVariable String userId ,@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        return ResponseEntity.ok(iUser.resetAdminUserPassword(userId, resetPasswordRequest));
    }
    @PutMapping("/{userId}/status")
    public ResponseEntity<?> changeUserStatus(@PathVariable String userId, @RequestParam EnumPool.SiteUserStatus siteUserStatus) {
        return ResponseEntity.ok(iUser.changeAdminUserStatus(userId, siteUserStatus));
    }
}
