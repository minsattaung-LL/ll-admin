package pro.linuxlab.reservation.superadmin.business.partnerwithus;

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
import pro.linuxlab.reservation.superadmin.entity.LLPartnerWithUs;
import pro.linuxlab.reservation.superadmin.service.PartnerWithUsService;
import pro.linuxlab.reservation.util.Util;

import java.util.List;

import static pro.linuxlab.reservation.superadmin.error.AdminErrorCode.Business.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class PartnerWithUsBusiness extends BaseBusiness implements IPartnerWithUs {
    final PartnerWithUsService partnerWithUsService;
    final Util util;
    @Override
    public BaseResponse getPartnerWithUs(String businessName, String businessType, String businessAddress, String firstName, String lastName, String primaryContactNumber, String email, String status, String updatedBy, int offset, int pageSize, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(offset, pageSize, Sort.by(sortBy, direction));
        try {
            return util.generateDefaultResponse(partnerWithUsService.getPartnerWithUsData(businessName, businessType, businessAddress, firstName, lastName, primaryContactNumber, email, status, updatedBy, pageable));
        } catch (Exception e) {
            log.error("error while getting site list: " + e);
            throw new BusinessException(DATABASE_ERROR);
        }
    }

    @Override
    public BaseResponse updatePartnerWithUsStatus(String id, EnumPool.PartnerStatus status) {
        LLPartnerWithUs partnerWithUs = (LLPartnerWithUs) findById(id, EnumPool.EntityConfig.PARTNER_WITH_US).orElseThrow(() -> new BusinessException(ENTITY_NOT_EXISTS));
        List<String> siteList = getSiteList();
        if (siteList.stream().noneMatch(x -> x.equals(partnerWithUs.getBusinessType()))) {
            throw new BusinessException(BUSINESS_TYPE_NOT_EXISTS);
        }
        partnerWithUs.setPartnerStatus(status);
        save(partnerWithUs, EnumPool.EntityConfig.PARTNER_WITH_US);
        /*
        * #TODO
        *   - send notification to staff with the required system
        * */
        return util.generateDefaultResponse(null);
    }
}
