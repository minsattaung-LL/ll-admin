package pro.linuxlab.reservation.superadmin.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.entity.LLCustomerInquiry;

public interface LLCustomerInquiryRepo extends JpaRepository<LLCustomerInquiry, String> {

    @Query(nativeQuery = true,
            value = "SELECT \n" +
                    "ci.customer_inquiry_id, \n" +
                    "c.customer_id, \n" +
                    "c.username as customer_username, \n" +
                    "c.name as customer_name, \n" +
                    "ci.email as customer_email, \n" +
                    "ci.inquiry_context, \n" +
                    "ci.inquiry_at, \n" +
                    "u.user_id as reply_user_id, \n" +
                    "u.username as reply_username, \n" +
                    "u.owner as reply_owner, \n" +
                    "u.user_role as reply_user_role, \n" +
                    "ci.reply_message, \n" +
                    "ci.reply_at, \n" +
                    "ci.status as reply_status \n" +
                    "FROM ll_customer_inquiry ci \n" +
                    "left join ll_user u on ci.reply_by = u.user_id \n" +
                    "left join ll_customer c on ci.customer_id = c.customer_id \n" +
                    "WHERE \n" +
                    "(:status is null or :status = '' or ci.status = :status) AND\n" +
                    "(:replyBy is null or :replyBy = '' or u.username like CONCAT('%', :replyBy, '%') or u.owner like CONCAT('%', :replyBy, '%')) \n"
    )
    Page<LLCustomerInquiry> getInquiryList(@Param("status") EnumPool.InquiryStatus status,
                                           @Param("replyBy") String replyBy,
                                           Pageable pageable);
}
