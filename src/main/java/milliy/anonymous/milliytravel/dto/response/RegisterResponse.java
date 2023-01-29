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
public class RegisterResponse {

    private Boolean isRegistered;

    private String message;

    private ProfileDTO profile;

    public RegisterResponse(Boolean isRegistered, ProfileDTO profile) {
        this.isRegistered = isRegistered;
        this.profile = profile;
    }

    public RegisterResponse(Boolean isRegistered) {
        this.isRegistered = isRegistered;
    }
}

