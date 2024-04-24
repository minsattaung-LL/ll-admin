package pro.linuxlab.reservation.superadmin.dto.inquriy;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AnswerInquiryRequest {
    @NotBlank
    String replyMessage;
}
