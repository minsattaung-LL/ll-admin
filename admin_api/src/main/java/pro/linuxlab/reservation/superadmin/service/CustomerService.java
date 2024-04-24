package pro.linuxlab.reservation.superadmin.service;

import pro.linuxlab.reservation.superadmin.entity.LLCustomer;

import java.util.Optional;

public interface CustomerService {
    Optional<LLCustomer> findById(String id);

    LLCustomer save(LLCustomer entity);
}
