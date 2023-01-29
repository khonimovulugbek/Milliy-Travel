package milliy.anonymous.milliytravel.dto.guide;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data

@NoArgsConstructor
public class GuideLanguageDTO {

    private  String id;

    private  LocalDateTime createdDate;

    private  LocalDateTime updatedDate;

    private  String languageId;

    private  String guideId;

    public GuideLanguageDTO(String languageId, String guideId) {
        this.languageId = languageId;
        this.guideId = guideId;
    }
}
