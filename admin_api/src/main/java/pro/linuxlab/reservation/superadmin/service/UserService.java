package pro.linuxlab.reservation.superadmin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pro.linuxlab.reservation.superadmin.entity.LLUser;
import pro.linuxlab.reservation.superadmin.projection.UserProjection;

import java.util.Optional;

public interface UserService {
    Optional<LLUser> findById(String id);

    LLUser save(LLUser entity);

    Page<UserProjection> getUserData(String search, String site, Pageable pageable);

    Optional<LLUser> findUserByUsername(String username);
}
