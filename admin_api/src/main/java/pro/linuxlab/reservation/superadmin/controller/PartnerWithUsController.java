package pro.linuxlab.reservation.superadmin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.linuxlab.reservation.superadmin.BaseResponse;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.business.partnerwithus.IPartnerWithUs;

@RestController
@CrossOrigin
@RequestMapping("/partner")
@RequiredArgsConstructor
public class PartnerWithUsController {
    final IPartnerWithUs iPartnerWithUs;
    @GetMapping("/get")
    public ResponseEntity<BaseResponse> getPartnerWithUs(@RequestParam(required = false) String businessName,
                                                         @RequestParam(required = false) String businessType,
                                                         @RequestParam(required = false) String businessAddress,
                                                         @RequestParam(required = false) String firstName,
                                                         @RequestParam(required = false) String lastName,
                                                         @RequestParam(required = false) String primaryContactNumber,
                                                         @RequestParam(required = false) String email,
                                                         @RequestParam(required = false) String status,
                                                         @RequestParam(required = false) String updatedBy,
                                                         @RequestParam(name = "offset", defaultValue = "0", required = false) int offset,
                                                         @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                         @RequestParam(name = "sortBy", defaultValue = "created_at", required = false) String sortBy,
                                                         @RequestParam(defaultValue = "desc") String direction) {
        return ResponseEntity.ok(iPartnerWithUs.getPartnerWithUs(businessName, businessType, businessAddress, firstName, lastName, primaryContactNumber, email, status, updatedBy, offset, pageSize, sortBy, direction));
    }
    @PutMapping("/{id}/update")
    public ResponseEntity<BaseResponse> updatePartnerWithUsStatus(@PathVariable String id, @RequestParam EnumPool.PartnerStatus status) {
        return ResponseEntity.ok(iPartnerWithUs.updatePartnerWithUsStatus(id, status));
    }
}
