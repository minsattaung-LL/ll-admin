package pro.linuxlab.reservation.superadmin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.entity.LLCustomerInquiry;

import java.util.Optional;

public interface CustomerInquiryService {
    Optional<LLCustomerInquiry> findById(String id);

    LLCustomerInquiry save(LLCustomerInquiry entity);

    Page<LLCustomerInquiry> getInquiryList(EnumPool.InquiryStatus status, String replyBy, Pageable pageable);
}
