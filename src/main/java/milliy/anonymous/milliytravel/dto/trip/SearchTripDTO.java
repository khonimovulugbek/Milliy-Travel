package milliy.anonymous.milliytravel.dto.trip;

import milliy.anonymous.milliytravel.dto.request.TripFilterDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchTripDTO {

    private String key;

    private TripFilterDTO filterTrip;
}
