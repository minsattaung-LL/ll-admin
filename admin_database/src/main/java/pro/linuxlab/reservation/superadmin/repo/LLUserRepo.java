package pro.linuxlab.reservation.superadmin.repo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.linuxlab.reservation.superadmin.entity.LLUser;
import pro.linuxlab.reservation.superadmin.projection.UserProjection;

import java.util.List;
import java.util.Optional;

public interface LLUserRepo  extends JpaRepository<LLUser, String> {
    @Query(nativeQuery = true,
            value = "SELECT \n" +
                    "    u.user_id, \n" +
                    "    u.kc_user_id, \n" +
                    "    u.username, \n" +
                    "    u.owner,\n" +
                    "    u.phone_number,\n" +
                    "    u.user_role,\n" +
                    "    u.Status, \n" +
                    "    u.description, \n" +
                    "    u.created_at, \n" +
                    "    u.updated_at, \n" +
                    "    GROUP_CONCAT(sc.site_name ORDER BY sc.site_name) AS site_list\n" +
                    "FROM ll_user u \n" +
                    "    JOIN ll_site_staff su ON u.user_id = su.user_id \n" +
                    "    JOIN ll_site_config sc ON sc.site_id = su.site_id\n" +
                    "WHERE \n" +
                    "    (:site IS NULL OR :site = '' OR sc.site_name = :site) AND \n" +
                    "    (:search IS NULL OR :search = '' OR u.user_id LIKE CONCAT('%',:search,'%') OR u.owner LIKE CONCAT('%',:search,'%')) \n" +
                    "GROUP BY \n" +
                    "    u.user_id, \n" +
                    "    u.kc_user_id, \n" +
                    "    u.username, \n" +
                    "    u.owner,\n" +
                    "    u.phone_number,\n" +
                    "    u.user_role,\n" +
                    "    u.Status, \n" +
                    "    u.description, \n" +
                    "    u.created_at, \n" +
                    "    u.updated_at")
    Page<UserProjection> getUserList(@Value("search") String search,
                                     @Value("site") String site,
                                     Pageable pageable);

    Optional<LLUser> findLLUserByUsername(String username);

}
