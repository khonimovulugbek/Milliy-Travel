package milliy.anonymous.milliytravel.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ProfileShortInfo {

    private String id;

    private String name;

    private String surname;

    private String photo;
}
