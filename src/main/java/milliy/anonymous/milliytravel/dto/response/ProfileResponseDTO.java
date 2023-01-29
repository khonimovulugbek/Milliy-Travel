package milliy.anonymous.milliytravel.dto.response;

import milliy.anonymous.milliytravel.dto.detail.DeviceDTO;
import milliy.anonymous.milliytravel.enums.AppLang;
import milliy.anonymous.milliytravel.enums.Gender;
import milliy.anonymous.milliytravel.enums.ProfileRole;
import milliy.anonymous.milliytravel.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@ToString
public class ProfileResponseDTO {

    private String id;

    private String name;

    private String surname;

    private String phoneNumber;

    private String email;

    private ProfileRole role;

    private String  guideId;

    private ProfileStatus status;

    private String photo;

    private Gender gender;

    private String birthDate;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private AppLang appLanguage;

    private List<DeviceDTO> devices;
}
