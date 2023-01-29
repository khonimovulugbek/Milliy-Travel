package milliy.anonymous.milliytravel.dto.profile;

import milliy.anonymous.milliytravel.annotation.FirstValidPhone;
import milliy.anonymous.milliytravel.annotation.ValidEmail;
import milliy.anonymous.milliytravel.dto.BaseDTO;
import milliy.anonymous.milliytravel.dto.detail.DeviceDTO;
import milliy.anonymous.milliytravel.enums.AppLang;
import milliy.anonymous.milliytravel.enums.Gender;
import milliy.anonymous.milliytravel.enums.ProfileRole;
import milliy.anonymous.milliytravel.enums.ProfileStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class ProfileDTO extends BaseDTO {

    @NotBlank(message = "Name required")
    private String name;

    @NotBlank(message = "Surname required")
    private String surname;

    @FirstValidPhone(message = "PhoneNumber required")
    private String phoneNumber;

    @ValidEmail(message = "Email required")
    private String email;

    private ProfileRole role;

    private ProfileStatus status;

    private String photo;

    @NotNull(message = "Gender required")
    private Gender gender;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private String birthDate;

    private AppLang appLanguage;

    private List<DeviceDTO> devices;

    private String guideId;

    private String token;

    public ProfileDTO(String id, String name, String surname, String photo) {
        super.id = id;
        this.name = name;
        this.surname = surname;
        this.photo = photo;
    }
}
