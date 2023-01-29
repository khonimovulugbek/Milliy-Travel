package milliy.anonymous.milliytravel.annotation;

import milliy.anonymous.milliytravel.validator.EmailValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {

    String message() default "";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};

}
