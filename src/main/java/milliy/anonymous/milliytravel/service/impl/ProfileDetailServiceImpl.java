package milliy.anonymous.milliytravel.service.impl;

import milliy.anonymous.milliytravel.dto.profile.ProfileDetailDTO;
import milliy.anonymous.milliytravel.entity.ProfileDetailEntity;
import milliy.anonymous.milliytravel.repository.ProfileDetailRepository;
import milliy.anonymous.milliytravel.service.ProfileDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileDetailServiceImpl implements ProfileDetailService {

    private final ProfileDetailRepository profileDetailRepository;


    public void create(ProfileDetailDTO dto) {

       dto.setPhoneNumber(dto.getPhoneNumber());

        ProfileDetailEntity entity = getByPhoneNumber(dto.getPhoneNumber());

        if (Optional.ofNullable(entity).isEmpty()) {
            entity = new ProfileDetailEntity();
        }

        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setSmsCode(dto.getSmsCode());
        entity.setAppLanguage(dto.getAppLanguage());
        entity.setUpdatedDate(LocalDateTime.now());

        profileDetailRepository.save(entity);
    }

    public ProfileDetailEntity getByPhoneNumber(String phoneNumber) {
        return profileDetailRepository
                .findByPhoneNumber(phoneNumber)
                .orElse(null);

    }

    public void delete(String id) {
        ProfileDetailEntity profileDetailEntity = profileDetailRepository.findById(id).orElse(new ProfileDetailEntity());
        profileDetailRepository.delete(profileDetailEntity);
    }
}
