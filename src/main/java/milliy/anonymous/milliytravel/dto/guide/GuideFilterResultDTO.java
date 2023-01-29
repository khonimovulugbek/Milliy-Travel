package milliy.anonymous.milliytravel.dto.guide;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@Setter
public class GuideFilterResultDTO  {

    private List<GuideShortInfoDTO> resultList;

    private Long maxResults;


}
