package milliy.anonymous.milliytravel.dto.request;

import milliy.anonymous.milliytravel.dto.detail.PriceDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class TripFilterDTO {

    private PriceDTO minPrice;
    private PriceDTO maxPrice;

    private Integer minPeople;
    private Integer maxPeople;

    private Integer minRate;
    private Integer maxRate;

    private Integer days;

}
