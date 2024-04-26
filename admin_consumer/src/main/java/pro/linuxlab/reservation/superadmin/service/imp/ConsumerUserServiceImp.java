package pro.linuxlab.reservation.superadmin.service.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.linuxlab.reservation.superadmin.entity.LLUser;
import pro.linuxlab.reservation.superadmin.repo.LLUserRepo;
import pro.linuxlab.reservation.superadmin.service.ConsumerUserService;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerUserServiceImp implements ConsumerUserService {
    final LLUserRepo llUserRepo;
    @Override
    public LLUser save(LLUser entity) {
        return llUserRepo.save(entity);
    }

    @Override
    public Optional<LLUser> findById(String userId) {
        return llUserRepo.findById(userId);
    }
}
