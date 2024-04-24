package pro.linuxlab.reservation.superadmin.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.linuxlab.reservation.superadmin.entity.LLSiteConfig;
import pro.linuxlab.reservation.superadmin.projection.SiteConfigProjection;
import pro.linuxlab.reservation.superadmin.projection.SiteIdProjection;

import java.util.List;
import java.util.Optional;

public interface LLSiteConfigRepo  extends JpaRepository<LLSiteConfig, String> {
    @Query(value = "Select sc.site_name from ll_site_config sc order by sc.site_id", nativeQuery = true)
    List<String> getSiteConfigList();

    Optional<LLSiteConfig> findLLSiteConfigBySiteName(String name);

    Optional<LLSiteConfig> findLLSiteConfigByKcClientId(String siteName);

    @Query(nativeQuery = true,
            value = "SELECT site_id, site_name FROM ll_site_config")
    List<SiteIdProjection> getSiteAndIdList();

    @Query(nativeQuery = true,
            value = "SELECT \n" +
                    "SC.site_id,\n" +
                    "SC.site_name,\n" +
                    "SC.kc_client_id,\n" +
                    "SC.kc_client_secret,\n" +
                    "SC.database_url,\n" +
                    "SC.database_name,\n" +
                    "SC.database_user,\n" +
                    "SC.database_password,\n" +
                    "SC.status,\n" +
                    "SC.description,\n" +
                    "GROUP_CONCAT(U.username) AS UsernameList,\n" +
                    "SC.created_at,\n" +
                    "SC.created_by,\n" +
                    "SC.updated_at,\n" +
                    "SC.updated_by\n" +
                    " FROM ll_site_config SC \n" +
                    "LEFT JOIN ll_site_staff SU ON SU.site_id = SC.site_id \n" +
                    "LEFT JOIN ll_user U ON U.user_id = SU.user_id\n" +
                    "WHERE \n" +
                    "    (:siteName IS NULL OR :siteName = '' OR SC.site_name LIKE CONCAT('%',:siteName, '%')) AND\n" +
                    "    (:kcClientId IS NULL OR :kcClientId = '' OR SC.kc_client_id LIKE CONCAT('%',:kcClientId, '%')) AND\n" +
                    "    (:kcClientSecret IS NULL OR :kcClientSecret = '' OR SC.kc_client_secret LIKE CONCAT('%',:kcClientSecret, '%')) AND\n" +
                    "    (:databaseUrl IS NULL OR :databaseUrl = '' OR SC.database_url LIKE CONCAT('%',:databaseUrl, '%')) AND\n" +
                    "    (:databaseName IS NULL OR :databaseName = '' OR SC.database_name LIKE CONCAT('%',:databaseName, '%')) AND\n" +
                    "    (:databaseUser IS NULL OR :databaseUser = '' OR SC.database_user LIKE CONCAT('%',:databaseUser, '%')) AND\n" +
                    "    (:databasePassword IS NULL OR :databasePassword = ''OR SC.database_password LIKE CONCAT('%',:databasePassword, '%')) AND\n" +
                    "    (:status IS NULL OR :status = ''OR SC.status LIKE CONCAT('%',:status, '%')) AND\n" +
                    "    (:description IS NULL OR :description = '' OR SC.description LIKE CONCAT('%',:description, '%'))\n" +
                    "GROUP BY SC.site_id,\n" +
                    "SC.site_name,\n" +
                    "SC.kc_client_id,\n" +
                    "SC.kc_client_secret,\n" +
                    "SC.database_url,\n" +
                    "SC.database_name,\n" +
                    "SC.database_user,\n" +
                    "SC.database_password,\n" +
                    "SC.status,\n" +
                    "SC.description,\n" +
                    "SC.created_at,\n" +
                    "SC.created_by,\n" +
                    "SC.updated_at,\n" +
                    "SC.updated_by\n")
    Page<SiteConfigProjection> getSiteConfigData(@Param(value = "siteName") String siteName,
                                                 @Param(value = "kcClientId") String kcClientId,
                                                 @Param(value = "kcClientSecret") String kcClientSecret,
                                                 @Param(value = "databaseUrl") String databaseUrl,
                                                 @Param(value = "databaseName") String databaseName,
                                                 @Param(value = "databaseUser") String databaseUser,
                                                 @Param(value = "databasePassword") String databasePassword,
                                                 @Param(value = "description") String description,
                                                 @Param(value = "status") String siteUserStatus,
                                                 Pageable pageable);
}
