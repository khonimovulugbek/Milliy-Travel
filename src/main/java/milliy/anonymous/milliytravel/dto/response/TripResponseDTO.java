package milliy.anonymous.milliytravel.dto.response;

import milliy.anonymous.milliytravel.dto.*;
import milliy.anonymous.milliytravel.dto.detail.LocationDTO;
import milliy.anonymous.milliytravel.dto.detail.PriceDTO;
import milliy.anonymous.milliytravel.dto.guide.GuideShortInfoDTO;
import milliy.anonymous.milliytravel.dto.request.CommentDTO;
import milliy.anonymous.milliytravel.dto.request.FacilityDTO;
import milliy.anonymous.milliytravel.dto.trip.TripPhotoResDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TripResponseDTO extends BaseDTO {


    private String name;

    private List<TripPhotoResDTO> photos;

    private List<FacilityDTO> facility;

    private List<LocationDTO> locations;

    private String description;

    private PriceDTO price;

    private Integer minPeople;

    private Integer maxPeople;

    private Integer days;

    private GuideShortInfoDTO guideProfile;

    private String phoneNumber;

    private Double rate;

    private List<String> attendancesProfileId;

    private List<CommentDTO> reviews;
}
