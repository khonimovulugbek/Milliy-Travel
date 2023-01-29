package milliy.anonymous.milliytravel.dto.profile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JwtDTO {

    private String phoneNumber;

    public JwtDTO(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
