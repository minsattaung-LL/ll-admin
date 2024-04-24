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
import pro.linuxlab.reservation.superadmin.EnumPool;

import java.time.LocalDateTime;

@Entity
@Table(name = "ll_partner_with_us", schema = "system_config_db")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LLPartnerWithUs {
    @Id
    @Column(name = "partner_with_us_id", unique = true, nullable = false)
    String partnerWithUsId;
    @Column(name = "business_name", nullable = false)
    String businessName;
    @Column(name = "business_type", nullable = false)
    String businessType;
    @Column(name = "business_address", nullable = false, columnDefinition = "TEXT")
    String businessAddress;
    @Column(name = "first_name", nullable = false)
    String firstName;
    @Column(name = "last_name", nullable = false)
    String lastName;
    @Column(name = "primary_contact_number", unique = true, nullable = false)
    String primaryContactNumber;
    @Column(name = "email", unique = true, nullable = false)
    String email;
    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    EnumPool.PartnerStatus partnerStatus;
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
