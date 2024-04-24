package pro.linuxlab.reservation.superadmin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import pro.linuxlab.reservation.superadmin.EnumPool.SiteUserStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "ll_site_config", schema = "system_config_db")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LLSiteConfig {
    @Id
    @Column(name = "site_id", unique = true, nullable = false)
    String siteId;
    @Column(name = "site_name", nullable = false)
    String siteName;

    @Column(name = "kc_client_id", unique = true, nullable = false)
    String kcClientId;
    @Column(name = "kc_client_secret", unique = true, nullable = false)
    String kcClientSecret;

    @Column(name = "database_url", unique = true, nullable = false)
    String databaseUrl;
    @Column(name = "database_name", unique = true, nullable = false)
    String databaseName;
    @Column(name = "database_user", unique = true, nullable = false)
    String databaseUser;
    @Column(name = "database_password", unique = true, nullable = false)
    String databasePassword;


    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    SiteUserStatus status;
    @Column(name = "description")
    String description;
    @Column(name = "created_at")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    @CreationTimestamp
    LocalDateTime createdAt;
    @Column(name = "created_by")
    String createdBy;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDateTime updatedAt;
    @Column(name = "updated_by")
    String updatedBy;

}
