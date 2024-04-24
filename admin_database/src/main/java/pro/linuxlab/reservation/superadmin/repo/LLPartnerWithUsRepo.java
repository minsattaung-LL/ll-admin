package pro.linuxlab.reservation.superadmin.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.linuxlab.reservation.superadmin.entity.LLPartnerWithUs;

public interface LLPartnerWithUsRepo extends JpaRepository<LLPartnerWithUs, String> {


    @Query(nativeQuery = true,
            value = "select * from ll_partner_with_us where \n" +
                    "(:businessName is null or :businessName is '' or business_name LIKE CONCAT('%',:businessName,'%')) AND \n" +
                    "(:businessType is null or :businessType is '' or business_type LIKE CONCAT('%',:businessType,'%')) AND \n" +
                    "(:businessAddress is null or :businessAddress is '' or business_address LIKE CONCAT('%',:businessAddress,'%')) AND \n" +
                    "(:firstName is null or :firstName is '' or first_name LIKE CONCAT('%',:firstName,'%')) AND \n" +
                    "(:lastName is null or :lastName is '' or last_name LIKE CONCAT('%',:lastName,'%')) AND \n" +
                    "(:primaryContactNumber is null or :primary_contact_number is '' or business_name LIKE CONCAT('%',:primaryContactNumber,'%')) AND \n" +
                    "(:email is null or :email is '' or email LIKE CONCAT('%',:email,'%')) AND \n" +
                    "(:status is null or :status is '' or status LIKE CONCAT('%',:status,'%')) AND \n" +
                    "(:updatedBy is null or :updatedBy is '' or updated_by LIKE CONCAT('%',:updatedBy,'%'))"
    )
    Page<LLPartnerWithUs> getPartnerWithUsData(@Param("businessName") String businessName,
                                               @Param("businessType") String businessType,
                                               @Param("businessAddress") String businessAddress,
                                               @Param("firstName") String firstName,
                                               @Param("lastName") String lastName,
                                               @Param("primaryContactNumber") String primaryContactNumber,
                                               @Param("email") String email,
                                               @Param("status") String status,
                                               @Param("updatedBy") String updatedBy,
                                               Pageable pageable);
}
