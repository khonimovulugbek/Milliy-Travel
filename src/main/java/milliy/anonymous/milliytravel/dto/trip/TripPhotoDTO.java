package milliy.anonymous.milliytravel.dto.trip;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TripPhotoDTO   {
    private  String id;
    private  String attachId;
    private  String tripId;
    private  String locationId;
}
