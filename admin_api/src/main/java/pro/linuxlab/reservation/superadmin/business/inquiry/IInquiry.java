package pro.linuxlab.reservation.superadmin.business.inquiry;

import pro.linuxlab.reservation.superadmin.BaseResponse;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.dto.inquriy.AnswerInquiryRequest;

public interface IInquiry {
    BaseResponse getInquiry(EnumPool.InquiryStatus status, String replyBy, int offset, int pageSize, String sortBy, String direction);

    BaseResponse answerInquiry(String id, AnswerInquiryRequest request);
}
