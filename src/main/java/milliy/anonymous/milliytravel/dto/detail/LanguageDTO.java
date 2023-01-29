package milliy.anonymous.milliytravel.dto.detail;

import milliy.anonymous.milliytravel.enums.LanguageLevel;
import milliy.anonymous.milliytravel.enums.LanguageName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class LanguageDTO {
    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LanguageName name;
    private LanguageLevel level;
}
