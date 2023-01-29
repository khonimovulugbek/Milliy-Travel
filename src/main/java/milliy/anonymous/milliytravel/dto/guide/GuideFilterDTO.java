package milliy.anonymous.milliytravel.dto.guide;

import milliy.anonymous.milliytravel.dto.detail.PriceDTO;
import milliy.anonymous.milliytravel.enums.Gender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GuideFilterDTO {

    private PriceDTO minPrice;

    private PriceDTO maxPrice;

    private Integer minRating;

    private Integer maxRating;

    private Gender gender;
}
