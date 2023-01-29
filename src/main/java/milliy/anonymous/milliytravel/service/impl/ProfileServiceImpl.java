package milliy.anonymous.milliytravel.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.config.details.EntityDetails;
import milliy.anonymous.milliytravel.dto.Result;
import milliy.anonymous.milliytravel.dto.detail.AttachDTO;
import milliy.anonymous.milliytravel.dto.profile.ProfileDTO;
import milliy.anonymous.milliytravel.dto.profile.ProfileShortInfo;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.dto.response.ProfileResponseDTO;
import milliy.anonymous.milliytravel.entity.GuideEntity;
import milliy.anonymous.milliytravel.entity.ProfileEntity;
import milliy.anonymous.milliytravel.enums.AppLang;
import milliy.anonymous.milliytravel.enums.ProfileRole;
import milliy.anonymous.milliytravel.enums.ProfileStatus;
import milliy.anonymous.milliytravel.exception.AppBadRequestException;
import milliy.anonymous.milliytravel.mapper.GuideProfileInfoMapper;
import milliy.anonymous.milliytravel.repository.GuideRepository;
import milliy.anonymous.milliytravel.repository.ProfileRepository;
import milliy.anonymous.milliytravel.service.AttachService;
import milliy.anonymous.milliytravel.service.DeviceService;
import milliy.anonymous.milliytravel.service.ProfileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    private final GuideRepository guideRepository;

    private final DeviceService deviceService;

    private final AttachService attachService;



    @Value("${profile.folder}")
    private String profileFolder;


    public ActionDTO create(ProfileDTO dto) {
        ProfileEntity profileEntity = getByPhoneNumber(dto.getPhoneNumber());
        if (Optional.ofNullable(profileEntity).isPresent()) {
            log.warn("profile already exists! :{}",profileEntity);
            return new ActionDTO("Profile", "Profile not found!", false);
        }

        ProfileEntity entity = new ProfileEntity();

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhoneNumber(dto.getPhoneNumber());

        entity.setRole(ProfileRole.ROLE_TOURIST);
        entity.setStatus(ProfileStatus.ACTIVE);

        entity.setGender(dto.getGender());
        entity.setAppLanguage(dto.getAppLanguage());

        if (Optional.ofNullable(dto.getEmail()).isPresent()) {
            entity.setEmail(dto.getEmail());
        }

        if (Optional.ofNullable(dto.getPhoto()).isPresent()) {
            entity.setPhoto(dto.getPhoto());
        }

        if (Optional.ofNullable(dto.getBirthDate()).isPresent()) {
            entity.setBirthDate(dto.getBirthDate());
        }

        try {
            profileRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            log.warn("Unique Phone Number {}", dto.getPhoneNumber());
            throw new AppBadRequestException("Unique Phone Number!");
        }

        return new ActionDTO(true);
    }

    public ProfileDTO update(ProfileDTO dto) {
        ProfileEntity entity = EntityDetails.getProfile();

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());

        entity.setGender(dto.getGender());

        if (Optional.ofNullable(dto.getAppLanguage()).isPresent()) {
            entity.setAppLanguage(dto.getAppLanguage());
        }

        if (Optional.ofNullable(dto.getEmail()).isPresent()) {
            entity.setEmail(dto.getEmail());
        }

        if (Optional.ofNullable(dto.getPhoto()).isPresent()) {
            entity.setPhoto(dto.getPhoto());
        }

        if (Optional.ofNullable(dto.getBirthDate()).isPresent()) {
            entity.setBirthDate(dto.getBirthDate());
        }

        entity.setUpdatedDate(LocalDateTime.now());

        profileRepository.save(entity);

        return toDTO(entity);
    }


    public ProfileDTO get(String id) {
        ProfileEntity entity = getById(id);
        return toDTO(entity);

    }

    public ProfileEntity getById(String id) {
        return profileRepository
                .findById(id)
                .orElse(null);
    }

    public AttachDTO uploadPhoto(MultipartFile file) {

        ProfileEntity profile = EntityDetails.getProfile();

        if (Optional.ofNullable(profile.getPhoto()).isPresent()) {
            var entity = attachService.getByLink(profile.getPhoto());
            attachService.delete(entity.getId());
        }

        AttachDTO attach = attachService.upload(file, profileFolder);

        if (attach == null) {
            log.warn("<< uploadPhoto attach is null");
            return null;
        }

        String openUrl = attach.getWebContentLink();

        profileRepository.updateImage(openUrl, profile.getId());

        return attach;
    }

    public Boolean changeStatus() {

        ProfileEntity entity = EntityDetails.getProfile();

        switch (entity.getStatus()) {
            case ACTIVE -> profileRepository.updateStatus(ProfileStatus.BLOCK, entity.getId());
            case BLOCK -> profileRepository.updateStatus(ProfileStatus.ACTIVE, entity.getId());
        }

        return true;
    }

    public PageImpl<ProfileDTO> profilePaginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<ProfileEntity> entityPage = profileRepository.findAll(pageable);

        List<ProfileDTO> dtoList = new ArrayList<>();
        entityPage.forEach(entity -> dtoList.add(toDTO(entity)));

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    public ProfileEntity getByPhoneNumber(String phoneNumber) {
        return profileRepository
                .findByPhoneNumberAndDeletedDateIsNull(phoneNumber)
                .orElse(null);
    }

    public GuideProfileInfoMapper getByPhoneNumberMapper(String phoneNumber) {
        return profileRepository
                .findByPhoneNumberMapper(phoneNumber)
                .orElse(null);
    }

    public ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setPhoto(entity.getPhoto());
        dto.setGender(entity.getGender());

        dto.setBirthDate(entity.getBirthDate());

        if (entity.getRole().equals(ProfileRole.ROLE_GUIDE)) {

            GuideEntity guide = guideRepository.findByProfileIdAndDeletedDateIsNull(entity.getId()).orElse(null);

            if(Optional.ofNullable(guide).isPresent()) dto.setGuideId(guide.getId());

        }


        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setAppLanguage(entity.getAppLanguage());
        dto.setDevices(deviceService.devicesByProfile(entity.getPhoneNumber()));
        return dto;
    }


    public ProfileResponseDTO toResponseDTO(ProfileEntity entity) {
        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setPhoto(entity.getPhoto());
        dto.setGender(entity.getGender());

        dto.setBirthDate(entity.getBirthDate());

        if (entity.getRole().equals(ProfileRole.ROLE_GUIDE)) {

            GuideEntity guide = guideRepository.findByProfileIdAndDeletedDateIsNull(entity.getId()).orElse(null);

            if(Optional.ofNullable(guide).isPresent()) dto.setGuideId(guide.getId());

        }

        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setAppLanguage(entity.getAppLanguage());
        dto.setDevices(deviceService.devicesByProfile(entity.getPhoneNumber()));

        return dto;
    }

    public ProfileDTO toDTOMapper(GuideProfileInfoMapper mapper) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(mapper.getP_id());
        dto.setName(mapper.getP_name());
        dto.setSurname(mapper.getP_surname());
        dto.setPhoneNumber(mapper.getP_phone_number());
        dto.setEmail(mapper.getP_email());
        dto.setRole(mapper.getP_role());
        dto.setStatus(mapper.getP_status());
        dto.setPhoto(mapper.getP_photo());
        dto.setGender(mapper.getP_gender());

        dto.setBirthDate(mapper.getP_birth_date());

        dto.setCreatedDate(mapper.getP_created_date());
        dto.setUpdatedDate(mapper.getP_updated_date());
        dto.setAppLanguage(mapper.getP_app_language());
        dto.setDevices(deviceService.devicesByProfile(mapper.getP_phone_number()));
        dto.setGuideId(mapper.getG_id());
        return dto;
    }


    public ProfileShortInfo toShortInfo(ProfileEntity entity) {
       return new ProfileShortInfo(entity.getId(), entity.getName(),entity.getSurname(),entity.getPhoto());
    }

    public ActionDTO delete() {
        ProfileEntity entity = EntityDetails.getProfile();

        if (Optional.ofNullable(entity).isEmpty()) {
            log.warn("<< delete profile is null");
            return new ActionDTO(false);
        }

        if (entity.getRole().equals(ProfileRole.ROLE_GUIDE)) {
            log.info("update profile role :{}",entity);
            guideRepository.updateDeleteDate(entity.getId(), LocalDateTime.now());
        }

        profileRepository.updateDeleteDate(entity.getId(), LocalDateTime.now());

        return new ActionDTO(true);
    }

    public void changeRole(ProfileRole tourist, String id) {
        log.info("update profileRole: id {}, role {}",id,tourist);
        profileRepository.updateRole(tourist, id);
    }

    public ActionDTO updateLanguage(AppLang lang) {

        ProfileEntity profile = EntityDetails.getProfile();

        profileRepository.updateAppLanguage(lang, profile.getPhoneNumber());

        return new ActionDTO(true);
    }
}
