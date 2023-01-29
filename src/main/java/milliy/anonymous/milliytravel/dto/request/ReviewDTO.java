package milliy.anonymous.milliytravel.dto.request;

import milliy.anonymous.milliytravel.enums.ReviewType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.*;

@Getter
@Setter
@ToString
public class ReviewDTO {


    @Min(value = 0, message = "rate must be between 0 and 5")
    @Max(value = 5, message = "rate must be between 0 and 5")
    private Integer rate;

    @NotBlank(message = "content required")
    private String content;

    @NotNull(message = "type required")
    private ReviewType type;

    private String tripId;

    private String guideId;
}


// reponse comment   profile dto
// if(trip)  trip dto
// if(guide)  guide dto
