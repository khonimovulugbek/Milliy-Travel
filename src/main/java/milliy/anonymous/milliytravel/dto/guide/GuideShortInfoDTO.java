package milliy.anonymous.milliytravel.dto.guide;

import milliy.anonymous.milliytravel.dto.detail.LanguageDTO;
import milliy.anonymous.milliytravel.dto.detail.LocationDTO;
import milliy.anonymous.milliytravel.dto.detail.PriceDTO;
import milliy.anonymous.milliytravel.enums.Currency;
import milliy.anonymous.milliytravel.enums.TourType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GuideShortInfoDTO {

    private String id;

    private String name;

    private String surname;

    private String profilePhoto;

    private Double rate;

    private String phone;

    private PriceDTO price;

    private List<LocationDTO> travelLocations;

    private List<LanguageDTO> languages;

    private int reviewCount;

    public GuideShortInfoDTO(String id, String name, String surname,
                             String profilePhoto, String priceId,
                             Currency currency, Long cost, TourType tourType, Double rate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.profilePhoto = profilePhoto;
        this.rate = rate;
        this.price = new PriceDTO(priceId, cost, currency, tourType);

    }
}
