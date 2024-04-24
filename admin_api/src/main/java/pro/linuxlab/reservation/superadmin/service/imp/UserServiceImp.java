package pro.linuxlab.reservation.superadmin.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pro.linuxlab.reservation.superadmin.entity.LLUser;
import pro.linuxlab.reservation.superadmin.projection.UserProjection;
import pro.linuxlab.reservation.superadmin.repo.LLUserRepo;
import pro.linuxlab.reservation.superadmin.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    final LLUserRepo llUserRepo;
    @Override
    public Optional<LLUser> findById(String id) {
        return llUserRepo.findById(id);
    }

    @Override
    public LLUser save(LLUser entity) {
        return llUserRepo.save(entity);
    }
    @Override
    public Page<UserProjection> getUserData(String search, String site, Pageable pageable) {
        return llUserRepo.getUserList(search, site, pageable);
    }

    @Override
    public Optional<LLUser> findUserByUsername(String username) {
        return llUserRepo.findLLUserByUsername(username);
    }
}
