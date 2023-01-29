package milliy.anonymous.milliytravel.dto.trip;


import milliy.anonymous.milliytravel.dto.detail.LocationDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TripPhotoResDTO {

    private String id;

    private String photo;

    private String tripId;

    private LocationDTO location;

}
