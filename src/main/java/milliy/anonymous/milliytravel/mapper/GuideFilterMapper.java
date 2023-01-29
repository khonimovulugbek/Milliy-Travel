package milliy.anonymous.milliytravel.mapper;

import milliy.anonymous.milliytravel.enums.Currency;
import milliy.anonymous.milliytravel.enums.TourType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class GuideFilterMapper {

    private String guideId;

    private String profileId;

    private String phone;

    private String bio;

    private Boolean hiring;

    private String priceId;

    private Long price_cost;

    private Currency price_currency;

    private TourType price_type;

    private List<String> language;

    private List<String> locations;

    private Double rate;

    private LocalDateTime createDate;

    public GuideFilterMapper(String guideId, String profileId,
                             String phone, String bio, Boolean hiring,
                             String priceId, Long price_cost, Currency price_currency,
                             TourType price_type, Object language, Object locations,
                             Double rate, LocalDateTime createDate) {
        this.guideId = guideId;
        this.profileId = profileId;
        this.phone = phone;
        this.bio = bio;
        this.hiring = hiring;
        this.priceId = priceId;
        this.price_cost = price_cost;
        this.price_currency = price_currency;
        this.price_type = price_type;
        this.language = (List<String>) language;
        this.locations = (List<String>) locations;
        this.rate = rate;
        this.createDate = createDate;
    }
}
