package pro.linuxlab.reservation.superadmin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.linuxlab.reservation.superadmin.entity.LLCustomer;

public interface LLCustomerRepo  extends JpaRepository<LLCustomer, String> {
}
