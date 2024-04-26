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
import pro.linuxlab.reservation.superadmin.EnumPool.UserRole;

import java.time.LocalDateTime;

@Entity
@Table(name = "ll_user", schema = "system_config_db")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LLUser {
    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    String userId;
    @Column(name = "kc_user_id", unique = true, nullable = false)
    String kcUserId;
    @Column(name = "username", unique = true, nullable = false)
    String username;
    @Column(name = "owner", nullable = false)
    String owner;
    @Column(name = "phone_number", unique = true, nullable = false)
    String phoneNumber;
    @Column(name = "user_role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    UserRole userRole;
    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    SiteUserStatus status;
    @Column(name = "description", columnDefinition = "TEXT")
    String description;
    @Column(name = "created_at")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    @CreationTimestamp
    LocalDateTime createdAt;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDateTime updatedAt;

    public LLUser(String userId) {
        this.userId = userId;
    }
}
