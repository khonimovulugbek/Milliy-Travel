package milliy.anonymous.milliytravel.dto.trip;

import milliy.anonymous.milliytravel.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TripFacilityDTO  extends BaseDTO {

    private  String facilityId;

    private  String tripId;

    public TripFacilityDTO(String facilityId, String tripId) {
        this.facilityId = facilityId;
        this.tripId = tripId;
    }
}
