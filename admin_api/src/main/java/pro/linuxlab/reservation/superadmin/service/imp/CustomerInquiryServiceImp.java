package pro.linuxlab.reservation.superadmin.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.entity.LLCustomerInquiry;
import pro.linuxlab.reservation.superadmin.repo.LLCustomerInquiryRepo;
import pro.linuxlab.reservation.superadmin.service.CustomerInquiryService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerInquiryServiceImp implements CustomerInquiryService {
    final LLCustomerInquiryRepo llCustomerInquiryRepo;
    @Override
    public Optional<LLCustomerInquiry> findById(String id) {
        return llCustomerInquiryRepo.findById(id);
    }

    @Override
    public LLCustomerInquiry save(LLCustomerInquiry entity) {
        return llCustomerInquiryRepo.save(entity);
    }

    @Override
    public Page<LLCustomerInquiry> getInquiryList(EnumPool.InquiryStatus status, String replyBy, Pageable pageable) {
        return llCustomerInquiryRepo.getInquiryList(status, replyBy, pageable);
    }
}
