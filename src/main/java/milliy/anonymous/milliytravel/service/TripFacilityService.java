package milliy.anonymous.milliytravel.service;

import milliy.anonymous.milliytravel.dto.request.FacilityDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.dto.trip.TripFacilityDTO;

import java.util.List;

public interface TripFacilityService {

    ActionDTO create(TripFacilityDTO dto);

    List<FacilityDTO> getFacilityByTripId(String tripId);

}
