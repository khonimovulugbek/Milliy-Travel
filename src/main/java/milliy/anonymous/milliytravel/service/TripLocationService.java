package milliy.anonymous.milliytravel.service;

import milliy.anonymous.milliytravel.dto.detail.LocationDTO;
import milliy.anonymous.milliytravel.dto.request.TripUploadPhotoDTO;

import java.util.List;


public interface TripLocationService {

    TripUploadPhotoDTO tripUploadPhoto(TripUploadPhotoDTO dto);


    List<LocationDTO> getLocationByTripId(String id);
}
