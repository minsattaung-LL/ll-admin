package pro.linuxlab.reservation.superadmin.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.util.StringUtils;
import pro.linuxlab.reservation.superadmin.Translator;
import pro.linuxlab.reservation.superadmin.EnumPool;

import java.lang.annotation.*;
import java.util.Arrays;

import static pro.linuxlab.reservation.superadmin.error.AdminErrorCode.Business.INVALID_STATUS;

@Documented
@Constraint(validatedBy = SiteUserStatusValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SiteUserStatusChecker {
    String message() default INVALID_STATUS;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
class SiteUserStatusValidator implements ConstraintValidator<SiteUserStatusChecker, String> {
        @Override
        public void initialize(SiteUserStatusChecker contactNumber) {
        }
        @Override
        public boolean isValid(String status, ConstraintValidatorContext cxt) {
            if (!StringUtils.hasLength(status)) return false;
            return Arrays.stream(EnumPool.SiteUserStatus.values()).anyMatch(x -> x.toString().equals(status));
        }
}
