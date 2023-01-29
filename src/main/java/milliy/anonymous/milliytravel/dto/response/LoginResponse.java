package milliy.anonymous.milliytravel.dto.response;

import milliy.anonymous.milliytravel.dto.profile.ProfileDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@NoArgsConstructor
public class LoginResponse {
    private Boolean status;

    private String title;

    private String message;

    private Boolean isExist;

    private Boolean isGuide;

    private ProfileDTO profile;

    private String guideId;



    public LoginResponse(Boolean isExist, Boolean isGuide, ProfileDTO profile, String guideId) {
        this.isExist = isExist;
        this.isGuide = isGuide;
        this.profile = profile;
        this.guideId = guideId;
    }

    public LoginResponse(Boolean status, String title, String message) {
        this.status = status;
        this.title = title;
        this.message = message;
    }

}

/*    val title: String? = null, // Incorrect Sms code
    val message: String? = null, // The code you entered is incorrect. Please try again*/