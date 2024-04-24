package pro.linuxlab.reservation.superadmin.business.partnerwithus;

import pro.linuxlab.reservation.BaseResponse;
import pro.linuxlab.reservation.superadmin.EnumPool;

public interface IPartnerWithUs {
    BaseResponse getPartnerWithUs(String businessName, String businessType, String businessAddress, String firstName, String lastName, String primaryContactNumber, String email, String status, String updatedBy, int offset, int pageSize, String sortBy, String direction);

    BaseResponse updatePartnerWithUsStatus(String id, EnumPool.PartnerStatus status);
}
