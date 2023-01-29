package milliy.anonymous.milliytravel.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.dto.detail.DeviceDTO;
import milliy.anonymous.milliytravel.entity.DeviceEntity;
import milliy.anonymous.milliytravel.mapper.ProfileDeviceInfoMapper;
import milliy.anonymous.milliytravel.repository.DeviceRepository;
import milliy.anonymous.milliytravel.service.DeviceService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;


    public DeviceDTO create(DeviceDTO dto, String profileId) {
        DeviceEntity entity = null;
        if (dto != null) {
            entity = findByDeviceIdAndDeviceTokenAndProfileDetailId(dto.getDeviceId(), dto.getDeviceToken(), profileId);
        } else {
            entity = findByDeviceIdAndDeviceTokenAndProfileDetailId(null, null, profileId);
        }

        if (entity == null) {
            entity = new DeviceEntity();
        }

        if (dto != null) {
            entity.setDeviceId(dto.getDeviceId());
            entity.setDeviceToken(dto.getDeviceToken());
            entity.setDeviceType(dto.getDeviceType());
        }

        entity.setProfileDetailId(profileId);
        entity.setUpdatedDate(LocalDateTime.now());
        deviceRepository.save(entity);
        return toDTO(entity);
    }

    public DeviceEntity findByDeviceIdAndDeviceTokenAndProfileDetailId(String deviceId, String deviceToken, String profileId) {
        return deviceRepository
                .findByDeviceIdAndDeviceTokenAndProfileDetailId(deviceId, deviceToken, profileId)
                .orElse(null);
    }

    public DeviceDTO toDTO(DeviceEntity entity) {
        DeviceDTO dto = new DeviceDTO();
        dto.setDeviceId(entity.getDeviceId());
        dto.setDeviceToken(entity.getDeviceToken());
        dto.setDeviceType(entity.getDeviceType());
        return dto;
    }

    public DeviceDTO toDTOMapper(ProfileDeviceInfoMapper mapper) {
        DeviceDTO dto = new DeviceDTO();
        dto.setDeviceId(mapper.getD_id());
        dto.setDeviceToken(mapper.getD_token());
        dto.setDeviceType(mapper.getD_type());
        return dto;
    }

    public List<DeviceDTO> devicesByProfile(String phoneNumber) {
        return deviceRepository
                .findAllDeviceByPhoneNumber(phoneNumber)
                .stream()
                .map(this::toDTOMapper)
                .toList();
    }


}
