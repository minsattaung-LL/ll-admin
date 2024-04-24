package pro.linuxlab.reservation.superadmin.dto.inquriy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.projection.InquiryProjection;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InquiryResponse {
    String customerInquiryId;
    String customerId;
    String customerUsername;
    String customerName;
    String customerEmail;
    String inquiryContext;
    LocalDateTime inquiryAt;
    String replyUserId;
    String replyUsername;
    String replyOwner;
    String replyUserRole;
    String replyMessage;
    LocalDateTime replyAt;
    EnumPool.InquiryStatus replyStatus;
    public InquiryResponse (InquiryProjection projection) {
        this.customerInquiryId = projection.getCustomer_Inquiry_Id();
        this.customerId = projection.getCustomer_Id();
        this.customerUsername = projection.getCustomer_Username();
        this.customerName = projection.getCustomer_Name();
        this.customerEmail = projection.getCustomer_Email();
        this.inquiryContext = projection.getInquiry_Context();
        this.inquiryAt = projection.getInquiry_At();
        this.replyUserId = projection.getReply_User_Id();
        this.replyUsername = projection.getReply_Username();
        this.replyOwner = projection.getReply_Owner();
        this.replyUserRole = projection.getReply_User_Role();
        this.replyMessage = projection.getReply_Message();
        this.replyAt = projection.getReply_At();
        this.replyStatus = projection.getReply_Status();
    }
}
