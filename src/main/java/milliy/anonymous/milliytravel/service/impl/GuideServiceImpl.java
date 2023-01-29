package milliy.anonymous.milliytravel.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.config.details.EntityDetails;
import milliy.anonymous.milliytravel.dto.Result;
import milliy.anonymous.milliytravel.dto.attendances.AttendancesResDTO;
import milliy.anonymous.milliytravel.dto.detail.GuideLocationDTO;
import milliy.anonymous.milliytravel.dto.detail.LanguageDTO;
import milliy.anonymous.milliytravel.dto.detail.LocationDTO;
import milliy.anonymous.milliytravel.dto.detail.PriceDTO;
import milliy.anonymous.milliytravel.dto.guide.*;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.dto.response.GuideResponse;
import milliy.anonymous.milliytravel.entity.*;
import milliy.anonymous.milliytravel.enums.AttendancesStatus;
import milliy.anonymous.milliytravel.enums.AttendancesType;
import milliy.anonymous.milliytravel.enums.ProfileRole;
import milliy.anonymous.milliytravel.enums.ReviewType;
import milliy.anonymous.milliytravel.mapper.GuideFilterMapper;
import milliy.anonymous.milliytravel.mapper.GuideRateMapper;
import milliy.anonymous.milliytravel.repository.*;
import milliy.anonymous.milliytravel.repository.custom.GuideFilterRepository;
import milliy.anonymous.milliytravel.service.*;
import milliy.anonymous.milliytravel.util.NumberUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuideServiceImpl implements GuideService {

    private final GuideRepository guideRepository;

    private final GuideLocationService guideLocationService;
    private final GuideLanguageService guideLanguageService;
    private final GuideFilterRepository guideFilterRepository;
    private final LocationService locationService;
    private final LanguageService languageService;
    private final PriceService priceService;

    private final ReviewServiceImpl reviewService;

    private final AttendancesService attendancesService;

    private final ReviewRepository reviewRepository;

    private final AttendancesRepository attendancesRepository;
    private final ProfileRepository profileRepository;
    private final LanguageRepository languageRepository;

    private final LocationRepository locationRepository;

    private final ProfileServiceImpl profileService;


    public GuideDTO create(GuideDTO dto) {

        ProfileEntity profileEntity = EntityDetails.getProfile();

        GuideEntity guideEntity = EntityDetails.getGuide();

        if (Optional.ofNullable(guideEntity).isPresent()) {
            log.warn("Guide Exists {}", profileEntity);
            return null;
        }

        PriceDTO price = priceService.create(dto.getPrice());


        GuideEntity entity = new GuideEntity();
        entity.setBiography(dto.getBiography());
        entity.setPhoneNumber(dto.getSecondPhoneNumber());
        entity.setPriceId(price.getId());
        entity.setProfileId(profileEntity.getId());

        profileRepository.updateRole(ProfileRole.ROLE_GUIDE, profileEntity.getId());

        guideRepository.save(entity);

        for (LanguageDTO language : dto.getLanguages()) {
            guideLanguageService.create(new GuideLanguageDTO(languageService.create(language).getId(), entity.getId()));
        }

        for (LocationDTO location : dto.getTravelLocations()) {
            guideLocationService.create(new GuideLocationDTO(locationService.create(location).getId(), entity.getId()));
        }
        return toDTO(entity);
    }


    public ActionDTO update(GuideDTO dto) {

        ProfileEntity profileEntity = EntityDetails.getProfile();

        GuideEntity guideEntity = EntityDetails.getGuide();

        if (Optional.ofNullable(guideEntity).isEmpty()) {
            log.warn("Guide Not Found {}", profileEntity);
            return null;
        }

        PriceDTO price = priceService.create(dto.getPrice());


        locationRepository.deleteAllById(guideLocationService.findLocationByGuideId(
                guideEntity.getId()).stream().map(GuideLocationEntity::getLocationId).toList());


        languageRepository.deleteAllById(guideLanguageService.findLanguageByGuideId(
                guideEntity.getId()).stream().map(GuideLanguageEntity::getLanguageId).toList());


        for (LanguageDTO language : dto.getLanguages()) {
            guideLanguageService.create(new GuideLanguageDTO(
                    languageService.create(language).getId(), guideEntity.getId()));
        }

        for (LocationDTO location : dto.getTravelLocations()) {
            guideLocationService.create(new GuideLocationDTO(
                    locationService.create(location).getId(), guideEntity.getId()));
        }

        guideEntity.setBiography(dto.getBiography());
        guideEntity.setPhoneNumber(dto.getSecondPhoneNumber());
        guideEntity.setPriceId(price.getId());
        guideEntity.setProfileId(profileEntity.getId());
        guideEntity.setUpdatedDate(LocalDateTime.now());

        guideRepository.save(guideEntity);

        return new ActionDTO(true);
    }

    public Boolean getGuideHiring() {

        GuideEntity guideEntity = EntityDetails.getGuide();

        if (Optional.ofNullable(guideEntity).isPresent()) {
            return guideEntity.getIsHiring();
        }

        return false;
    }

    public List<GuideShortInfoDTO> getTop10(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "rate"));

        List<GuideRateMapper> rate = guideRepository.getRate(pageable, ProfileRole.ROLE_GUIDE);

        return rate.stream().map(this::toShortDTO).toList();
    }


    public GuideShortInfoDTO toShortDTO(GuideRateMapper mapper) {

        GuideShortInfoDTO dto = new GuideShortInfoDTO();

        dto.setPrice(new PriceDTO(mapper.getPriceId(), mapper.getPrice_cost(), mapper.getPrice_currency(), mapper.getPrice_type()));

        if (Optional.ofNullable(mapper.getRate()).isPresent()) {
            dto.setRate(mapper.getRate());
        } else {
            dto.setRate(0d);
        }

        dto.setId(mapper.getGuideId());

        dto.setTravelLocations(guideLocationService.getLocationByGuideId(mapper.getGuideId()));

        dto.setLanguages(guideLanguageService.getLanguageByGuideId(mapper.getGuideId()));

        dto.setName(mapper.getName());

        dto.setSurname(mapper.getSurname());

        dto.setProfilePhoto(mapper.getPhoto());

        dto.setReviewCount(reviewRepository.getGuideReviewsCount(mapper.getGuideId()));

        dto.setPhone(mapper.getPhone());

        return dto;
    }


    public GuideDTO toDTO(GuideFilterMapper mapper) {
        GuideDTO dto = new GuideDTO();

        dto.setPrice(new PriceDTO(mapper.getPriceId(), mapper.getPrice_cost(), mapper.getPrice_currency(), mapper.getPrice_type()));

        dto.setRate(getRateByGuideId(mapper.getGuideId()));

        dto.setCreatedDate(mapper.getCreateDate());

        dto.setBiography(mapper.getBio());

        dto.setId(mapper.getGuideId());

        dto.setProfileId(mapper.getProfileId());

        dto.setIsHiring(mapper.getHiring());

        dto.setSecondPhoneNumber(mapper.getPhone());


        if (mapper.getLanguage().size() > 0) {
            dto.setLanguages(languageRepository.findAllById(mapper.getLanguage()).
                    stream().map(languageService::toDTO).toList());
        }

        if (mapper.getLocations().size() > 0) {

            dto.setTravelLocations(locationRepository.findAllById(mapper.getLocations())
                    .stream().map(locationService::toDTO).toList());

        }

        return dto;
    }

    public GuideDTO toDTO(GuideEntity entity) {

        GuideDTO dto = new GuideDTO();

        dto.setPrice(priceService.get(entity.getPriceId()));

        dto.setRate(getRateByGuideId(entity.getId()));

        dto.setCreatedDate(entity.getCreatedDate());

        dto.setBiography(entity.getBiography());

        dto.setId(entity.getId());

        dto.setProfileId(entity.getProfileId());

        dto.setProfile(profileService.get(entity.getProfileId()));

        dto.setIsHiring(entity.getIsHiring());

        dto.setSecondPhoneNumber(entity.getPhoneNumber());

        dto.setLanguages(guideLanguageService.getLanguageByGuideId(entity.getId()));

        dto.setTravelLocations(guideLocationService.getLocationByGuideId(entity.getId()));

        dto.setAttendances(
                attendancesService.getAttendances(
                        new AttendancesResDTO(entity.getId(), AttendancesType.GUIDE)));



        return dto;
    }

    public GuideEntity get(String id) {
        return guideRepository
                .findByIdAndDeletedDateIsNull(id)
                .orElse(null);
    }

    public GuideDTO getGuide(String guideId) {

        Thread thread = new Thread(() -> {
            List<AttendancesEntity> entityList = attendancesRepository
                    .findByStatusAndCreatedDateIsAfter(
                            AttendancesStatus.NOT_ACCEPTED, LocalDateTime.now().plusDays(1));

            if (!entityList.isEmpty()) {

                attendancesRepository.updateStatus(AttendancesStatus.ACCEPTED,
                        LocalDateTime.now(), entityList.
                                stream().
                                map(AttendancesEntity::getId).
                                toList());

            }
        });
        thread.start();


        GuideEntity guideEntity = get(guideId);

        if (Optional.ofNullable(guideEntity).isEmpty()) {
            log.warn("Guide profile not found! :{}", guideId);
            return null;
        }

        GuideDTO guideDTO = toDTO(guideEntity);

        guideDTO.setIsComment(reviewService.isComment(ReviewType.GUIDE,EntityDetails.getProfile().getId(),guideEntity.getId()));

        return guideDTO;
    }

    public Double getRateByGuideId(String guideId) {

        GuideEntity guideEntity = get(guideId);

        if (Optional.ofNullable(guideEntity).isEmpty()) {
            log.warn("Guide not Found! getRateByGuideId={}", guideId);
            return 0D;
        }

        Double rate = guideRepository.getRateByGuideId(guideId, ProfileRole.ROLE_GUIDE);

        if (Optional.ofNullable(rate).isEmpty()) {
            return 0d;
        }

        return rate;

    }


    public GuideShortInfoDTO getGuideShort(String guideId) {

        Optional<GuideRateMapper> optional = guideRepository.getShortInfo(guideId);

        if (optional.isEmpty()) {
            log.warn("Guide profile not found! :{}", guideId);
            return null;
        }

        return toShortDTO(optional.get());
    }


    public Boolean updateIsHiring() {

        ProfileEntity profileEntity = EntityDetails.getProfile();

        GuideEntity guideEntity = EntityDetails.getGuide();

        if (Optional.ofNullable(guideEntity).isEmpty()) {
            log.warn("Guide Not Found {}", profileEntity);
            return false;
        }

        if (guideEntity.getIsHiring()) {
            guideRepository.updateIsHiring(profileEntity.getId(), false);
            return false;
        } else {
            guideRepository.updateIsHiring(profileEntity.getId(), true);
            return true;
        }
    }


    public ActionDTO delete() {
        ProfileEntity profileEntity = EntityDetails.getProfile();

        GuideEntity guideEntity = EntityDetails.getGuide();

        if (Optional.ofNullable(guideEntity).isEmpty()) {
            log.warn("Guide Not Found {}", profileEntity);
            return new ActionDTO(false);
        }

        profileService.changeRole(ProfileRole.ROLE_TOURIST, profileEntity.getId());

        guideRepository.updateDeleteDate(guideEntity.getId(), LocalDateTime.now());

        return new ActionDTO(true);
    }


    public GuideResDTO filter(GuideSearchDTO dto, int page) {

        int size;

        if (page == 0) {
            size = 0;
        } else {
            size = (page) * 15;
        }

        GuideFilterResultDTO filter = guideFilterRepository.filter(dto, size);

        List<GuideShortInfoDTO> dtoList = filter.getResultList();

        dtoList.forEach(guide -> {
            guide.setTravelLocations(guideLocationService.getLocationByGuideId(guide.getId()));
        });


        return new GuideResDTO(dtoList, filter.getMaxResults(),
                        NumberUtils.getTotalPage(filter.getMaxResults(), dtoList.size()));
    }


    public GuideResponse toResponseDTO(GuideEntity entity) {

        GuideResponse dto = new GuideResponse();

        dto.setPrice(priceService.get(entity.getPriceId()));

        dto.setRate(getRateByGuideId(entity.getId()));

        dto.setCreatedDate(entity.getCreatedDate());

        dto.setBiography(entity.getBiography());

        dto.setId(entity.getId());

        dto.setProfileId(entity.getProfileId());

        dto.setProfile(profileService.toResponseDTO(profileService.getById(entity.getProfileId())));

        dto.setIsHiring(entity.getIsHiring());

        dto.setSecondPhoneNumber(entity.getPhoneNumber());

        dto.setLanguages(guideLanguageService.getLanguageByGuideId(entity.getId()));

        dto.setTravelLocations(guideLocationService.getLocationByGuideId(entity.getId()));

        return dto;
    }
}
