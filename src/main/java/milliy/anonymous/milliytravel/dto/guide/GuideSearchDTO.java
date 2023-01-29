package milliy.anonymous.milliytravel.dto.guide;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GuideSearchDTO {

    private String key;

    private GuideFilterDTO filterGuide;
}
