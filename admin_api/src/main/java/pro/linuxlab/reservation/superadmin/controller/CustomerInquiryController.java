package pro.linuxlab.reservation.superadmin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.linuxlab.reservation.superadmin.BaseResponse;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.business.inquiry.IInquiry;
import pro.linuxlab.reservation.superadmin.dto.inquriy.AnswerInquiryRequest;

@RestController
@CrossOrigin
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class CustomerInquiryController {
    final IInquiry iInquiry;
    @GetMapping("/get")
    public ResponseEntity<BaseResponse> getInquiry (@RequestParam(required = false) EnumPool.InquiryStatus status,
                                                    @RequestParam( required = false) String replyBy,
                                                    @RequestParam(name = "offset", defaultValue = "0", required = false) int offset,
                                                    @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                    @RequestParam(name = "sortBy", defaultValue = "created_at", required = false) String sortBy,
                                                    @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(iInquiry.getInquiry(status, replyBy, offset, pageSize, sortBy, direction));
    }
    @PutMapping("/{id}/answer")
    public ResponseEntity<BaseResponse> answerInquiry(@PathVariable String id, @RequestBody @Valid AnswerInquiryRequest request) {
        return ResponseEntity.ok(iInquiry.answerInquiry(id, request));
    }
}
