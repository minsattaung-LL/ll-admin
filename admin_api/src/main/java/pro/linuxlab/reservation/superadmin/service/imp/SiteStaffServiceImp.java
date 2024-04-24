package pro.linuxlab.reservation.superadmin.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.linuxlab.reservation.superadmin.entity.LLSiteStaff;
import pro.linuxlab.reservation.superadmin.repo.LLSiteStaffRepo;
import pro.linuxlab.reservation.superadmin.service.SiteStaffService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SiteStaffServiceImp implements SiteStaffService {
    final LLSiteStaffRepo llSiteStaffRepo;
    @Override
    public Optional<LLSiteStaff> findById(String id) {
        return llSiteStaffRepo.findById(id);
    }

    @Override
    public LLSiteStaff save(LLSiteStaff entity) {
        return llSiteStaffRepo.save(entity);
    }
}
