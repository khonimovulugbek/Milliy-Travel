package milliy.anonymous.milliytravel.annotation;

import milliy.anonymous.milliytravel.validator.FirstPhoneValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FirstPhoneValidator.class)
@Documented
public @interface FirstValidPhone {

    String message() default "";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};

}
