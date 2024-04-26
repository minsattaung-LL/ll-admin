package pro.linuxlab.reservation.superadmin.exception;

import pro.linuxlab.reservation.superadmin.BaseResponse;

public class ErrorExceptionMessage extends BaseResponse {
    public ErrorExceptionMessage(String status, String message) {
        super(status, null, message);
    }
}
