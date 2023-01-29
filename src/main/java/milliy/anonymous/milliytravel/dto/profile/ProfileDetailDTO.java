package milliy.anonymous.milliytravel.dto.profile;

import milliy.anonymous.milliytravel.annotation.FirstValidPhone;
import milliy.anonymous.milliytravel.dto.detail.DeviceDTO;
import milliy.anonymous.milliytravel.enums.AppLang;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;

@Setter
@Getter
@ToString
public class ProfileDetailDTO {

    @FirstValidPhone(message = "PhoneNumber required")
    private String phoneNumber;

    private String smsCode;
    private String sms;

    private DeviceDTO device;

    @NotNull(message = "AppLanguage required")
    private AppLang appLanguage;


}
