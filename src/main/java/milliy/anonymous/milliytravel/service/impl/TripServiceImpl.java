package milliy.anonymous.milliytravel.service.impl;

import milliy.anonymous.milliytravel.config.details.EntityDetails;
import milliy.anonymous.milliytravel.dto.Result;
import milliy.anonymous.milliytravel.dto.attendances.AttendancesResDTO;
import milliy.anonymous.milliytravel.dto.detail.LocationDTO;
import milliy.anonymous.milliytravel.dto.detail.PriceDTO;
import milliy.anonymous.milliytravel.dto.guide.GuideShortInfoDTO;
import milliy.anonymous.milliytravel.dto.guide.GuideTripsDTO;
import milliy.anonymous.milliytravel.dto.request.FacilityDTO;
import milliy.anonymous.milliytravel.dto.request.TripFirstDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.dto.response.TripResponseDTO;
import milliy.anonymous.milliytravel.dto.trip.*;
import milliy.anonymous.milliytravel.entity.GuideEntity;
import milliy.anonymous.milliytravel.entity.ProfileEntity;
import milliy.anonymous.milliytravel.entity.TripEntity;
import milliy.anonymous.milliytravel.entity.TripLocationEntity;
import milliy.anonymous.milliytravel.enums.AttendancesType;
import milliy.anonymous.milliytravel.enums.ReviewType;
import milliy.anonymous.milliytravel.exception.ItemNotFoundException;
import milliy.anonymous.milliytravel.mapper.TripFilterInfoMapper;
import milliy.anonymous.milliytravel.mapper.TripRateMapper;
import milliy.anonymous.milliytravel.mapper.trip.TripInfoMapper;
import milliy.anonymous.milliytravel.repository.*;
import milliy.anonymous.milliytravel.repository.custom.TripFilterRepository;
import milliy.anonymous.milliytravel.service.*;
import milliy.anonymous.milliytravel.util.NumberUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final GuideRepository guideRepository;

    private final TripLocationRepository tripLocationRepository;
    private final TripFilterRepository tripFilterRepository;
    private final FacilityRepository facilityRepository;
    private final LocationRepository locationRepository;

    private final GuideLanguageService guideLanguageService;

    private final GuideLocationService guideLocationService;

    private final TripPhotosRepository tripPhotosRepository;

    private final TripPhotoService tripPhotoService;
    private final LocationService locationService;
    private final PriceService priceService;

    private final AttachService attachService;
    private final ReviewServiceImpl reviewService;
    private final AttendancesService attendancesService;
    private final FacilityService facilityService;
    private final GuideServiceImpl guideService;

    private final TripFacilityService tripFacilityService;

    private final TripLocationService tripLocationService;

    public TripFirstDTO create(TripFirstDTO dto) {

        ProfileEntity profileEntity = EntityDetails.getProfile();

        GuideEntity guideEntity = EntityDetails.getGuide();

        if (Optional.ofNullable(guideEntity).isEmpty()) {
            log.warn("Not access {} ", profileEntity);
            return null;
        }

        TripEntity entity = new TripEntity();

        entity.setDescription(dto.getDesc());
        entity.setName(dto.getName());
        entity.setMaxPeople(dto.getMaxPeople());
        entity.setMinPeople(dto.getMinPeople());
        entity.setGuideId(guideEntity.getId());
        entity.setDays(dto.getDays());
        entity.setRate(0D);


        tripRepository.save(entity);

        dto.getTravelLocations().forEach(location -> {
            tripLocationRepository.save(new TripLocationEntity(locationService.create(location).getId(), entity.getId()));
        });

        dto.setId(entity.getId());
        return dto;
    }

    public ActionDTO update(TripFirstDTO dto, String tripId) {

        ProfileEntity profileEntity = EntityDetails.getProfile();

        GuideEntity guideEntity = EntityDetails.getGuide();

        if (Optional.ofNullable(guideEntity).isEmpty()) {
            log.warn("Not access {} ", profileEntity);
            return new ActionDTO(false);
        }

        TripEntity entity = get(tripId);

        if (entity == null) {
            log.warn("<< update trip is null");
            return new ActionDTO(false);
        }

        entity.setDescription(dto.getDesc());
        entity.setName(dto.getName());
        entity.setMaxPeople(dto.getMaxPeople());
        entity.setMinPeople(dto.getMinPeople());
        entity.setDays(dto.getDays());


        tripRepository.save(entity);

        return new ActionDTO(true);
    }

    public ActionDTO updateDetail(TripEntity tripEntity, String priceId) {

        tripEntity.setPriceId(priceId);
        tripRepository.save(tripEntity);

        return new ActionDTO(true);
    }

    public PageImpl<TripShortInfoDTO> getTrip(int page, int size) {

        ProfileEntity profileEntity = EntityDetails.getProfile();

        GuideEntity guideEntity = EntityDetails.getGuide();

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));


        Page<TripRateMapper> entityPage = tripRepository
                .getByGuideId(guideEntity.getId(), pageable);

        List<TripShortInfoDTO> tripDTOS = entityPage.stream().map(this::toDTOMapper).toList();

        tripDTOS.forEach(dto -> {
            dto.setPhoto(tripPhotoService.getPhotoByTripId(dto.getId()));
        });

        return new PageImpl<>(tripDTOS, pageable, entityPage.getTotalElements());
    }

    public TripResDTO filter(SearchTripDTO dto, int page) {

        int size;
        if (page == 0) {
            size = 0;
        } else {
            size = (page) * 15;
        }

        TripFilterResultDTO tripDTOS = tripFilterRepository.filter(dto, size);

        tripDTOS.getResultList().forEach(trip -> {
            trip.setPhoto(tripPhotoService.getPhotoByTripId(trip.getId()));
            trip.setRate(getTripRateByTripId(trip.getId()));
            trip.setReviewsCount(reviewService.getReviewsTripCount(trip.getId()));
        });

        return new TripResDTO(tripDTOS.getResultList(),
                        NumberUtils.getTotalPage(tripDTOS.getMaxResults(),
                                tripDTOS.getResultList().size()), tripDTOS.getResultList().size());
    }


    public TripResDTO getTrips(String guideId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

//        Page<TripEntity> tripPage = tripRepository.getTripByGuideId(guideId, pageable);
        Page<TripInfoMapper> tripPage = tripRepository.findTripByGuideIdMapper(guideId, pageable);

        List<GuideTripsDTO> dtoList = tripPage
                .stream()
                .map(this::toDTOInfoMapper)
                .toList();

        TripResDTO dto = new TripResDTO();
        dto.setTrips(dtoList);
        dto.setTotalPage(tripPage.getTotalPages());
        dto.setCurrentPageNumber(page);
        dto.setCurrentPageItems(dtoList.size());
        dto.setTotalItems(tripPage.getTotalElements());

        return dto;
    }


    public List<TripShortInfoDTO> top10(int page, int size) {

        Pageable of = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "rate"));

        Page<TripRateMapper> mapperPage = tripRepository.getRate(of);

        List<TripShortInfoDTO> tripList = new LinkedList<>();

        mapperPage.forEach(mapper -> {
            TripShortInfoDTO dto = toDTOMapper(mapper);

            dto.setPhoto(tripPhotoService.getPhotoByTripId(mapper.getId()));

            tripList.add(dto);
        });

        return tripList;
    }


    public TripEntity get(String tripId) {

        Optional<TripEntity> optional = tripRepository.findByIdAndDeletedDateIsNull(tripId);

        return optional.orElse(null);
    }

    public ActionDTO deleteTrip(String tripId) {

        ProfileEntity profile = EntityDetails.getProfile();

        TripEntity tripEntity = tripRepository.findByIdAndDeletedDateIsNull(tripId).orElse(null);

        if (Optional.ofNullable(tripEntity).isEmpty()) {
            log.warn("trip not found! : {}", tripId);
            return new ActionDTO(false);
        }

        if (!tripEntity.getGuide().getProfileId().equals(profile.getId())) {
            log.warn("not access trip delete : {}", tripId);
            return new ActionDTO(false);
        }

        tripRepository.updateDeletedDate(tripId, LocalDateTime.now());

        return new ActionDTO(true);
    }

    public List<TripPhotoResDTO> tripPhotoList(String tripId) {
        ProfileEntity profileEntity = EntityDetails.getProfile();

        TripInfoMapper mapper = getOrThrow(tripId);

        return tripPhotosRepository
                .findAllByTripId(mapper.getT_id())
                .stream()
                .map(tripPhotoService::toDTO)
                .toList();
    }

    public List<LocationDTO> tripLocationList(String tripId) {
        ProfileEntity profileEntity = EntityDetails.getProfile();

        TripInfoMapper mapper = getOrThrow(tripId);

        return tripLocationService.getLocationByTripId(mapper.getT_id());
    }

    public List<FacilityDTO> tripFacilityList(String tripId) {
        ProfileEntity profileEntity = EntityDetails.getProfile();

        TripInfoMapper mapper = getOrThrow(tripId);

        return tripFacilityService.getFacilityByTripId(mapper.getT_id());

    }

    public TripInfoMapper getOrThrow(String id) {
        return tripRepository
                .findByIdMapper(id)
                .orElseThrow(() -> {
                    log.warn("Trip Not Found! id={}", id);
                    return new ItemNotFoundException("Trip Not Found!");
                });
    }

    public TripShortInfoDTO getShortTrip(String tripId) {

        Optional<TripRateMapper> optional = tripRepository.getByTripId(tripId);

        if (optional.isEmpty()) {
            log.warn("Trip not found! getShortTrip ={}", tripId);
            return null;
        }

        return toDTOMapper(optional.get());
    }

    public GuideTripsDTO toDTOInfoMapper(TripInfoMapper mapper) {
        GuideTripsDTO dto = new GuideTripsDTO();
        dto.setId(mapper.getT_id());
        dto.setName(mapper.getT_name());
//        dto.setDescription(mapper.getT_description());
//        dto.setPhoneNumber(mapper.getT_phone_number());
        dto.setPrice(new PriceDTO(mapper.getP_id(), mapper.getP_cost(), mapper.getP_currency(), mapper.getP_type()));
        dto.setRate(mapper.getT_rate());
//        dto.setGuideId(mapper.getG_id());
//
//        dto.setMaxPeople(mapper.getT_max_people());
//        dto.setMinPeople(mapper.getT_min_people());
//        dto.setDays(mapper.getT_days());
//        dto.setCreatedDate(mapper.getT_created_date());
//        dto.setUpdatedDate(mapper.getT_updated_date());

//        dto.setAttendances(attendancesService
//                .getAttendances(new AttendancesResDTO(mapper.getT_id(), AttendancesType.TRIP)));

        dto.setPhoto(tripPhotoService.getPhotoByTripId(mapper.getT_id()));

        dto.setReviewsCount(reviewService.getReviewsTripCount(mapper.getT_id()));

        return dto;
    }

    /**
     * toDTO
     */

    public TripShortInfoDTO toDTOMapper(TripRateMapper mapper) {
        TripShortInfoDTO dto = new TripShortInfoDTO();
        dto.setName(mapper.getTitle());
        dto.setPrice(new PriceDTO(mapper.getPrice_id(), mapper.getPrice_cost(), mapper.getPrice_currency(), mapper.getPrice_type()));

        if (Optional.ofNullable(mapper.getRate()).isPresent()) {
            dto.setRate(mapper.getRate());
        } else {
            dto.setRate(0d);
        }
        dto.setPhoto(tripPhotoService.getPhotoByTripId(mapper.getId()));
        dto.setId(mapper.getId());
        dto.setReviewsCount(reviewService.getReviewsTripCount(mapper.getId()));
        return dto;
    }


    public TripDTO toDTOMapper(TripFilterInfoMapper mapper) {
        TripDTO dto = new TripDTO();
        dto.setId(mapper.getT_id());
        dto.setName(mapper.getT_name());
        dto.setDescription(mapper.getT_desc());
        dto.setPhoneNumber(mapper.getT_phone());
        dto.setMaxPeople(mapper.getT_max());
        dto.setMinPeople(mapper.getT_min());
        dto.setDays(mapper.getT_days());
        dto.setRate(mapper.getT_rate());

        dto.setPrice(new PriceDTO(mapper.getP_id(), mapper.getP_cost(), mapper.getP_currency(), mapper.getP_type()));

        GuideShortInfoDTO guideShortInfoDTO = new GuideShortInfoDTO(mapper.getG_id(), mapper.getGp_name(), mapper.getGp_surname(), mapper.getGp_photo(),
                mapper.getP_id(), mapper.getP_currency(), mapper.getP_cost(), mapper.getP_type(), mapper.getG_rate());

        guideShortInfoDTO.setTravelLocations(guideLocationService.getLocationByGuideId(mapper.getG_id()));

        guideShortInfoDTO.setLanguages(guideLanguageService.getLanguageByGuideId(mapper.getG_id()));

        dto.setGuide(guideShortInfoDTO);

        dto.setFacilities(mapper.getFacilities()
                .stream()
                .map(facilityService::get)
                .toList());

        dto.setLocations(mapper.getLocations()
                .stream()
                .map(locationService::get)
                .toList());

        // Trip Filter check it

        return dto;
    }

    public TripDTO toDTO(TripEntity entity) {

        TripDTO dto = new TripDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setPrice(priceService.get(entity.getPriceId()));
        dto.setGuideId(entity.getGuideId());
        dto.setRate(getTripRateByTripId(entity.getId()));
        dto.setGuide(guideService.getGuideShort(entity.getGuideId()));

        dto.setMaxPeople(entity.getMaxPeople());
        dto.setMinPeople(entity.getMinPeople());
        dto.setDays(entity.getDays());
        dto.setCreatedDate(entity.getCreatedDate());

        dto.setFacilities(tripFacilityService.getFacilityByTripId(entity.getId()));

        dto.setLocations(tripLocationService.getLocationByTripId(entity.getId()));

        dto.setPhotos(tripPhotoService.getByTripId(entity.getId()));

        dto.setAttendances(
                attendancesService.getAttendances(
                        new AttendancesResDTO(entity.getId(), AttendancesType.TRIP)));


        return dto;
    }

    public TripResponseDTO toResponseDTO(TripEntity entity) {
        TripResponseDTO dto = new TripResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setPrice(priceService.get(entity.getPriceId()));
        dto.setGuideProfile(guideService.getGuideShort(entity.getGuideId()));
        dto.setMaxPeople(entity.getMaxPeople());
        dto.setMinPeople(entity.getMinPeople());
        dto.setDays(entity.getDays());
        dto.setCreatedDate(entity.getCreatedDate());

        dto.setFacility(tripFacilityService.getFacilityByTripId(entity.getId()));

        dto.setLocations(tripLocationService.getLocationByTripId(entity.getId()));

        dto.setPhotos(tripPhotosRepository.findAllByTripId(
                entity.getId()).stream().map(tripPhotoService::toDTO).toList());


        return dto;
    }


    public TripDTO getTripById(String tripId) {

        TripEntity tripEntity = get(tripId);

        if (tripEntity == null) {
            log.warn("trip not found! : {}", tripId);
            return null;
        }

        TripDTO tripDTO = toDTO(tripEntity);

        tripDTO.setIsComment(reviewService.isComment(ReviewType.TRIP, EntityDetails.getProfile().getId(), tripEntity.getId()));

        return tripDTO;

    }

    public Double getTripRateByTripId(String tripId) {

        TripEntity tripEntity = get(tripId);

        if (Optional.ofNullable(tripEntity).isEmpty()) {
            log.warn("trip not found! getTripRateByTripId ={}", tripId);
            return 0D;
        }

        Double rate = tripRepository.getRateByTripId(tripId);

        if (Optional.ofNullable(rate).isEmpty()) {
            return 0d;
        }

        return rate;
    }
}
