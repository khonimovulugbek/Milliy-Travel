package milliy.anonymous.milliytravel.dto.guide;

import milliy.anonymous.milliytravel.dto.BaseDTO;
import milliy.anonymous.milliytravel.dto.detail.PriceDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GuideTripsDTO extends BaseDTO {

    private String name;

    private Double rate;

    private int reviewsCount;

    private PriceDTO price;

    private String photo;

}
