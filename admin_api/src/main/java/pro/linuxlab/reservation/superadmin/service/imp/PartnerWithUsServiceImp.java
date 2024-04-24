package pro.linuxlab.reservation.superadmin.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.entity.LLPartnerWithUs;
import pro.linuxlab.reservation.superadmin.repo.LLPartnerWithUsRepo;
import pro.linuxlab.reservation.superadmin.service.PartnerWithUsService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartnerWithUsServiceImp implements PartnerWithUsService {
    final LLPartnerWithUsRepo llPartnerWithUsRepo;
    @Override
    public Optional<LLPartnerWithUs> findById(String id) {
        return llPartnerWithUsRepo.findById(id);
    }

    @Override
    public LLPartnerWithUs save(LLPartnerWithUs entity) {
        return llPartnerWithUsRepo.save(entity);
    }

    @Override
    public Page<LLPartnerWithUs> getPartnerWithUsData(String businessName, String businessType, String businessAddress, String firstName, String lastName, String primaryContactNumber, String email, String status, String updatedBy, Pageable pageable) {
        return llPartnerWithUsRepo.getPartnerWithUsData(businessName, businessType, businessAddress, firstName, lastName, primaryContactNumber, email, status, updatedBy, pageable);
    }
}
