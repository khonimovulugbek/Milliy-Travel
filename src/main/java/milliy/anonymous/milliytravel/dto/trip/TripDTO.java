package milliy.anonymous.milliytravel.dto.trip;

import milliy.anonymous.milliytravel.dto.BaseDTO;
import milliy.anonymous.milliytravel.dto.attendances.AttendancesRequest;
import milliy.anonymous.milliytravel.dto.detail.LocationDTO;
import milliy.anonymous.milliytravel.dto.detail.PriceDTO;
import milliy.anonymous.milliytravel.dto.guide.GuideShortInfoDTO;
import milliy.anonymous.milliytravel.dto.request.FacilityDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TripDTO extends BaseDTO {

    private String name;

    private String description;

    private String phoneNumber;

    private Double rate;

    private Integer maxPeople;
    private Integer minPeople;

    private Integer days;

    private String priceId;
    private PriceDTO price;

    private String guideId;
    private GuideShortInfoDTO guide;

    private List<TripPhotoResDTO> photos;
    private List<FacilityDTO> facilities;
    private List<LocationDTO> locations;

    private List<AttendancesRequest> attendances;

    private Boolean isComment;


}
