package milliy.anonymous.milliytravel.dto.request;

import milliy.anonymous.milliytravel.dto.BaseDTO;
import milliy.anonymous.milliytravel.dto.profile.ProfileShortInfo;
import milliy.anonymous.milliytravel.dto.trip.TripShortInfoDTO;
import milliy.anonymous.milliytravel.enums.ReviewType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDTO extends BaseDTO {

    private Integer rate;

    private String reviewTime;

    private String reviewContent;

    private ProfileShortInfo from;

    private ReviewType commentType;

    private TripShortInfoDTO trip;

    private ProfileShortInfo guide;

    private String answerTime;

    private String answerContent;
}
