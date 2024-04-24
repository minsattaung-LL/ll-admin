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
import pro.linuxlab.reservation.superadmin.EnumPool.InquiryStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "ll_customer_inquiry", schema = "system_config_db")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LLCustomerInquiry {
    @Id
    @Column(name = "customer_inquiry_id")
    String customerInquiryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    LLCustomer llCustomer;
    @Column(name = "email", nullable = false)
    String email;
    @Column(name = "inquiry_context", columnDefinition = "TEXT", nullable = false)
    String inquiryContext;
    @Column(name = "inquiry_at")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    @CreationTimestamp
    LocalDateTime inquiryAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    LLUser llUser;
    @Column(name = "reply_message", columnDefinition = "TEXT")
    String replyMessage;
    @Column(name = "reply_at")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    LocalDateTime replyAt;
    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    InquiryStatus inquiryStatus;
}
