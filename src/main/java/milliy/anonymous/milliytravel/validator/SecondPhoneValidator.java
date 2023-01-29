package milliy.anonymous.milliytravel.validator;

import milliy.anonymous.milliytravel.annotation.SecondValidPhone;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecondPhoneValidator implements ConstraintValidator<SecondValidPhone, String> {
    private static final
    String PHONE_PATTERN = "(?:\\+[0-9]{12})";


    @Override
    public void initialize(SecondValidPhone constraintAnnotation) {
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        return (validatePhone(phone));
    }

    private boolean validatePhone(String phone) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        if (Optional.ofNullable(phone).isPresent()) {
            Matcher matcher = pattern.matcher(phone);
            return matcher.matches();
        }
        return true;
    }

}
