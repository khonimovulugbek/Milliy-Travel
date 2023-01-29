package milliy.anonymous.milliytravel.dto.response;

import milliy.anonymous.milliytravel.dto.guide.GuideShortInfoDTO;
import milliy.anonymous.milliytravel.dto.trip.TripShortInfoDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MainResponseDTO extends ActionDTO {

    private List<GuideShortInfoDTO> topGuides;

    private List<TripShortInfoDTO> topTrips;

}
