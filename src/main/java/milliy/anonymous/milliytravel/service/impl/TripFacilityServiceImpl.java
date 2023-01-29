package milliy.anonymous.milliytravel.service.impl;

import milliy.anonymous.milliytravel.dto.request.FacilityDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.dto.trip.TripFacilityDTO;
import milliy.anonymous.milliytravel.entity.TripEntity;
import milliy.anonymous.milliytravel.entity.TripFacilityEntity;
import milliy.anonymous.milliytravel.mapper.TripFacilityMapper;
import milliy.anonymous.milliytravel.repository.TripFacilityRepository;
import milliy.anonymous.milliytravel.repository.TripRepository;
import milliy.anonymous.milliytravel.service.FacilityService;
import milliy.anonymous.milliytravel.service.TripFacilityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TripFacilityServiceImpl implements TripFacilityService {


    private final TripRepository tripRepository;

    private final TripFacilityRepository tripFacilityRepository;

    private final FacilityService facilityService;

    public ActionDTO create(TripFacilityDTO dto) {

        Optional<TripEntity> tripEntity = tripRepository.getTrip(dto.getTripId());

        if (tripEntity.isEmpty()) {
            log.warn("Trip not found! TripFacilityServiceImpl = {}", dto.getTripId());
            return new ActionDTO(false);
        }

        FacilityDTO facilityDTO = facilityService.get(dto.getFacilityId());

        if (Optional.ofNullable(facilityDTO).isEmpty()) {
            log.warn("Facility not found! TripFacilityServiceImpl = {}", dto.getFacilityId());
            return new ActionDTO(false);
        }

        TripFacilityEntity entity = new TripFacilityEntity();
        entity.setFacilityId(dto.getFacilityId());
        entity.setTripId(dto.getTripId());

        tripFacilityRepository.save(entity);

        return new ActionDTO(true);
    }


    public List<FacilityDTO> getFacilityByTripId(String tripId) {
        return tripFacilityRepository.getFacilityByTripId(tripId).stream().map(this::toDTO).toList();

    }

    private FacilityDTO toDTO(TripFacilityMapper mapper) {
        return new FacilityDTO(mapper.getF_id(), mapper.getF_title(), mapper.getF_description());
    }
}
