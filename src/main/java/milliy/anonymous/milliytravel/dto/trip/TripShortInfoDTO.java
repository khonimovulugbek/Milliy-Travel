package milliy.anonymous.milliytravel.dto.trip;

import milliy.anonymous.milliytravel.dto.detail.PriceDTO;
import milliy.anonymous.milliytravel.enums.Currency;
import milliy.anonymous.milliytravel.enums.TourType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TripShortInfoDTO {

    private String id;
    private String name;
    private String photo;
    private PriceDTO price;
    private Double rate;
    private int reviewsCount;

    public TripShortInfoDTO(String id, String name,  Double rate, String priceId,
                            Currency currency, Long cost, TourType tourType){
        this.id = id;
        this.name = name;
        this.rate =rate;
        this.price= new PriceDTO(priceId,cost,currency,tourType);
    }
}
