package milliy.anonymous.milliytravel.service.impl;

import milliy.anonymous.milliytravel.config.details.EntityDetails;
import milliy.anonymous.milliytravel.dto.detail.PriceDTO;
import milliy.anonymous.milliytravel.dto.request.TripSecondDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.dto.trip.TripFacilityDTO;
import milliy.anonymous.milliytravel.dto.trip.TripPhotoResDTO;
import milliy.anonymous.milliytravel.entity.*;
import milliy.anonymous.milliytravel.exception.AppForbiddenException;
import milliy.anonymous.milliytravel.repository.FacilityRepository;
import milliy.anonymous.milliytravel.repository.TripPhotosRepository;
import milliy.anonymous.milliytravel.repository.TripRepository;
import milliy.anonymous.milliytravel.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripPhotoServiceImpl implements TripPhotoService {


    private final TripPhotosRepository tripPhotosRepository;

    private final TripFacilityService tripFacilityService;

    private final FacilityRepository facilityRepository;

    private final TripRepository tripRepository;



    @Autowired
    @Lazy
    private TripService tripService;

    private final PriceService priceService;

    private final LocationService locationService;


    public ActionDTO create(String tripId, TripSecondDTO dto) {

        ProfileEntity profile = EntityDetails.getProfile();

        Optional<TripEntity> trip = tripRepository.getTrip(tripId);

        if (trip.isEmpty()) {
            log.warn("Trip Not Found {} ", tripId);
            return new ActionDTO("Trip", "Trip Not Found", false);
        }

        TripEntity tripEntity = trip.get();

        if (!tripEntity.getGuide().getProfileId().equals(profile.getId())) {
            log.warn("TripPhotoService Not access profile={}", profile);
            return new ActionDTO("Trip", "Not Access", false);
        }

        tripPhotosRepository.updateTripId(dto.getPhotos(), tripEntity.getId());


        dto.getFacilities().forEach(facility -> {

            FacilityEntity entity = new FacilityEntity();
            entity.setTitle(facility.getTitle());
            entity.setDescription(facility.getDescription());
            tripFacilityService.create(new TripFacilityDTO(facilityRepository.save(entity).getId(), tripEntity.getId()));

        });

        PriceDTO priceDTO = priceService.create(dto.getPrice());



        tripRepository.updatePriceId(tripEntity.getId(),priceDTO.getId());

        return new ActionDTO(true);
    }

    public TripPhotoResDTO toDTO(TripPhotoEntity entity) {

        TripPhotoResDTO dto = new TripPhotoResDTO();

        dto.setId(entity.getId());
        dto.setTripId(entity.getTripId());
        dto.setPhoto(entity.getPhoto());
        dto.setLocation(locationService.get(entity.getLocationId()));

        return dto;
    }

    public TripPhotoResDTO toDTOPhoto(TripPhotoEntity entity) {

        TripPhotoResDTO dto = new TripPhotoResDTO();

        dto.setId(entity.getId());
        dto.setTripId(entity.getTripId());
        dto.setPhoto(entity.getPhoto());

        return dto;
    }


    public List<TripPhotoResDTO> getByTripId(String id) {
        return tripPhotosRepository.findAllByTripId(id)
                .stream()
                .map(this::toDTO)
                .toList();
    }


    /**
     * LIMIT 1 PHOTO URL
     *
     * @param id
     * @return String
     */
    public String getPhotoByTripId(String id) {

        Optional<TripPhotoEntity> entity = tripPhotosRepository.findTopByTripIdOrderByCreatedDateAsc(id);

        if (entity.isEmpty()) {
            log.warn("getPhotoByTripId not found! ->{}", id);
            return "";
        }

        return toDTO(entity.get()).getPhoto();

    }

}
