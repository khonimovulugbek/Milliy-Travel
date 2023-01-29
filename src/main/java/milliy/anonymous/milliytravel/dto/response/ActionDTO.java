package milliy.anonymous.milliytravel.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ActionDTO {
    private String title;
    private String message;
    private Boolean status;

    public ActionDTO(String title, String message, Boolean status) {
        this.title = title;
        this.message = message;
        this.status = status;
    }

    public ActionDTO() {
    }

    public ActionDTO(Boolean status) {
        this.status = status;
    }
}
