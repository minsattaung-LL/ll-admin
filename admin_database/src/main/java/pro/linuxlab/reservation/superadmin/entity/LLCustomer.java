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
import pro.linuxlab.reservation.superadmin.EnumPool.Gender;
import pro.linuxlab.reservation.superadmin.EnumPool.SiteUserStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ll_customer", schema = "system_config_db")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LLCustomer {
    @Id
    @Column(name = "customer_id", unique = true, nullable = false)
    String customerId;
    @Column(name = "username", unique = true, nullable = false)
    String username;
    @Column(name = "email", unique = true, nullable = false)
    String email;
    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "gender", nullable = false)
    @Enumerated(value = EnumType.STRING)
    Gender gender;
    @Column(name = "phone_number", unique = true, nullable = false)
    String phoneNumber;
    @Column(name = "date_of_birth", nullable = false)
    LocalDate dateOfBirth;
    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    SiteUserStatus siteUserStatus;
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
    @Column(name = "updated_by")
    String updatedBy;
}
