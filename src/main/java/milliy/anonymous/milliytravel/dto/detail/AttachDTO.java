package milliy.anonymous.milliytravel.dto.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class AttachDTO {
    private String id;

    private String webViewLink;
    private String webContentLink;

    public AttachDTO(String id, String webContentLink) {
        this.id = id;
        this.webContentLink = webContentLink;
    }
}
