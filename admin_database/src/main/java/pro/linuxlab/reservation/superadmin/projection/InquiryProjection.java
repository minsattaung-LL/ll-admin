package pro.linuxlab.reservation.superadmin.projection;

import pro.linuxlab.reservation.superadmin.EnumPool;

import java.time.LocalDateTime;

public interface InquiryProjection {
    String getCustomer_Inquiry_Id();
    String getCustomer_Id();
    String getCustomer_Username();
    String getCustomer_Name();
    String getCustomer_Email();
    String getInquiry_Context();
    LocalDateTime getInquiry_At();
    String getReply_User_Id();
    String getReply_Username();
    String getReply_Owner();
    String getReply_User_Role();
    String getReply_Message();
    LocalDateTime getReply_At();
    EnumPool.InquiryStatus getReply_Status();
}
