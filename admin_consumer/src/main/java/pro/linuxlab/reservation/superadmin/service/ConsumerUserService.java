package pro.linuxlab.reservation.superadmin.service;

import pro.linuxlab.reservation.superadmin.entity.LLUser;

import java.util.Optional;

public interface ConsumerUserService {
    LLUser save(LLUser entity);

    Optional<LLUser> findById(String userId);
}
