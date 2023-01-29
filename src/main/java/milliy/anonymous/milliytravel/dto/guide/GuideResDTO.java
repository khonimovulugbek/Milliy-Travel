package milliy.anonymous.milliytravel.dto.guide;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GuideResDTO {

    private List<GuideShortInfoDTO> guideList;

    private Integer currentPageNumber;

    private Long currentPageItems;

    private Integer totalPage;


    public GuideResDTO(List<GuideShortInfoDTO> guideList, Long currentPageItems,Integer totalPage) {
        this.guideList = guideList;
        this.currentPageItems = currentPageItems;
        this.totalPage = totalPage;
    }
}
