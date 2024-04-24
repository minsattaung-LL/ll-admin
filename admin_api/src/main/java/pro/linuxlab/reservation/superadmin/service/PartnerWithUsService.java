package pro.linuxlab.reservation.superadmin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.entity.LLPartnerWithUs;

import java.util.Optional;

public interface PartnerWithUsService {
    Optional<LLPartnerWithUs> findById(String id);

    LLPartnerWithUs save(LLPartnerWithUs entity);

    Page<LLPartnerWithUs> getPartnerWithUsData(String businessName, String businessType, String businessAddress, String firstName, String lastName, String primaryContactNumber, String email, String status, String updatedBy, Pageable pageable);
}
