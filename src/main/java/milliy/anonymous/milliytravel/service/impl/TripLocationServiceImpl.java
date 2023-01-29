package milliy.anonymous.milliytravel.service.impl;

import milliy.anonymous.milliytravel.dto.detail.LocationDTO;
import milliy.anonymous.milliytravel.dto.request.TripUploadPhotoDTO;
import milliy.anonymous.milliytravel.entity.TripEntity;
import milliy.anonymous.milliytravel.entity.TripPhotoEntity;
import milliy.anonymous.milliytravel.mapper.trip.TripLocationMapper;
import milliy.anonymous.milliytravel.repository.AttachRepository;
import milliy.anonymous.milliytravel.repository.TripLocationRepository;
import milliy.anonymous.milliytravel.repository.TripPhotosRepository;
import milliy.anonymous.milliytravel.repository.TripRepository;
import milliy.anonymous.milliytravel.service.LocationService;
import milliy.anonymous.milliytravel.service.TripLocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripLocationServiceImpl implements TripLocationService {

    private final TripLocationRepository tripLocationRepository;

    private final TripRepository tripRepository;

    private final TripPhotosRepository tripPhotosRepository;

    private final AttachRepository attachRepository;

    private final LocationService locationService;


    public TripUploadPhotoDTO tripUploadPhoto(TripUploadPhotoDTO dto) {

        TripPhotoEntity entity = new TripPhotoEntity();
        LocationDTO locationDTO = locationService.create(dto.getLocation());
        entity.setLocationId(locationDTO.getId());
        entity.setPhoto(attachRepository.findById(dto.getPhotoId()).get().getWebContentLink());

        tripPhotosRepository.save(entity);

        return new TripUploadPhotoDTO(entity.getId(), entity.getPhoto(), locationDTO);

    }


    public List<LocationDTO> getLocationByTripId(String tripId) {

        TripEntity tripEntity = tripRepository.findByIdAndDeletedDateIsNull(tripId).orElse(null);

        if (Optional.ofNullable(tripEntity).isEmpty()) {
            log.warn("Trip Not Found! getLocationByTripId ={}", tripId);
            return null;
        }

        return tripPhotosRepository.getLocationByTripId(tripId).stream().map(this::toDTO).toList();
    }

    private LocationDTO toDTO(TripLocationMapper mapper) {

        LocationDTO dto = new LocationDTO();
        dto.setDescription(mapper.getDescription());
        dto.setProvence(mapper.getProvence());
        dto.setId(mapper.getLocationId());
        dto.setDistrict(mapper.getDistrict());

        return dto;
    }


}
