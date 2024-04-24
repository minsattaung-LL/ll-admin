package pro.linuxlab.reservation.superadmin.business.inquiry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import pro.linuxlab.reservation.BaseResponse;
import pro.linuxlab.reservation.exception.BusinessException;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.business.BaseBusiness;
import pro.linuxlab.reservation.superadmin.dto.inquriy.AnswerInquiryRequest;
import pro.linuxlab.reservation.superadmin.service.CustomerInquiryService;
import pro.linuxlab.reservation.util.Util;

import static pro.linuxlab.reservation.superadmin.error.AdminErrorCode.Business.DATABASE_ERROR;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomerInquiryBusiness extends BaseBusiness implements IInquiry {
    final Util util;
    final CustomerInquiryService customerInquiryService;
    @Override
    public BaseResponse getInquiry(EnumPool.InquiryStatus status, String replyBy, int offset, int pageSize, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(offset, pageSize, Sort.by(sortBy, direction));
        try {
            return util.generateDefaultResponse(customerInquiryService.getInquiryList(status, replyBy, pageable));
        } catch (Exception e) {
            log.error("error while getting site list: " + e);
            throw new BusinessException(DATABASE_ERROR);
        }
    }

    @Override
    public BaseResponse answerInquiry(String id, AnswerInquiryRequest request) {
        /*
        * TODO
        *  - send email to customer
        *  - save in database
        * */
        return util.generateDefaultResponse(null);
    }
}
