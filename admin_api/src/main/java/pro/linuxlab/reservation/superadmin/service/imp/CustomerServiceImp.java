package pro.linuxlab.reservation.superadmin.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.linuxlab.reservation.superadmin.entity.LLCustomer;
import pro.linuxlab.reservation.superadmin.repo.LLCustomerRepo;
import pro.linuxlab.reservation.superadmin.service.CustomerService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImp implements CustomerService {
    final LLCustomerRepo llCustomerRepo;

    @Override
    public Optional<LLCustomer> findById(String id) {
        return llCustomerRepo.findById(id);
    }

    @Override
    public LLCustomer save(LLCustomer entity) {
        return llCustomerRepo.save(entity);
    }
}
