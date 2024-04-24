package pro.linuxlab.reservation.superadmin.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.linuxlab.reservation.superadmin.entity.LLSiteStaff;
import pro.linuxlab.reservation.superadmin.entity.LLUser;
import pro.linuxlab.reservation.superadmin.repo.LLSiteStaffRepo;
import pro.linuxlab.reservation.superadmin.service.ConsumerSiteUserService;

@Service
@RequiredArgsConstructor
public class ConsumerSiteUserServiceImp implements ConsumerSiteUserService {
    final LLSiteStaffRepo llSiteStaffRepo;

    @Override
    public void save(LLSiteStaff entity) {
        llSiteStaffRepo.save(entity);
    }

    @Override
    public void deleteSiteAndUserByUserId(LLUser entity) {
        llSiteStaffRepo.deleteByLlUser(entity);
    }
}
