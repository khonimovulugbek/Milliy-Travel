package milliy.anonymous.milliytravel.service;

import milliy.anonymous.milliytravel.dto.request.TripSecondDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.dto.trip.TripPhotoResDTO;
import milliy.anonymous.milliytravel.entity.TripPhotoEntity;

import java.util.List;


public interface TripPhotoService {

     ActionDTO create(String tripId, TripSecondDTO dto);

     TripPhotoResDTO toDTO(TripPhotoEntity entity);

     TripPhotoResDTO toDTOPhoto(TripPhotoEntity entity);

     List<TripPhotoResDTO> getByTripId(String id);

     String getPhotoByTripId(String id);
}
