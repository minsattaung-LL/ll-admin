package pro.linuxlab.reservation.superadmin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import pro.linuxlab.reservation.superadmin.entity.LLSiteStaff;
import pro.linuxlab.reservation.superadmin.entity.LLUser;

public interface LLSiteStaffRepo  extends JpaRepository<LLSiteStaff, String> {
    @Transactional
    @Modifying
    void deleteByLlUser(LLUser llUser);
}
