package pro.linuxlab.reservation.superadmin.exception;

import pro.linuxlab.reservation.superadmin.Translator;

public class BusinessException extends ErrorCommon{
    public BusinessException(String errorCode) {
        super(errorCode, Translator.toLocale(errorCode));
    }
}
