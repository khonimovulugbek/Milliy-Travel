package milliy.anonymous.milliytravel.service;

import milliy.anonymous.milliytravel.dto.Result;
import milliy.anonymous.milliytravel.dto.detail.LocationDTO;
import milliy.anonymous.milliytravel.dto.request.FacilityDTO;
import milliy.anonymous.milliytravel.dto.request.TripFirstDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.dto.response.TripResponseDTO;
import milliy.anonymous.milliytravel.dto.trip.*;
import milliy.anonymous.milliytravel.entity.TripEntity;
import milliy.anonymous.milliytravel.mapper.TripFilterInfoMapper;
import milliy.anonymous.milliytravel.mapper.TripRateMapper;
import org.springframework.data.domain.PageImpl;

import java.util.List;


public interface TripService {

    TripFirstDTO create(TripFirstDTO dto);

    ActionDTO update(TripFirstDTO dto, String tripId);

    ActionDTO updateDetail(TripEntity tripEntity, String priceId);

    TripResDTO filter(SearchTripDTO dto, int page);

    TripResDTO getTrips(String guideId, int page, int size);

    ActionDTO deleteTrip(String tripId);

    List<TripPhotoResDTO> tripPhotoList(String tripId);

    List<LocationDTO> tripLocationList(String tripId);

    List<FacilityDTO> tripFacilityList(String tripId);

    PageImpl<TripShortInfoDTO> getTrip(int page, int size);

}
