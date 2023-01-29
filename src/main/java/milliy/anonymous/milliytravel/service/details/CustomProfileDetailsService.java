package milliy.anonymous.milliytravel.service.details;

import milliy.anonymous.milliytravel.config.details.CustomProfileDetails;
import milliy.anonymous.milliytravel.entity.ProfileEntity;
import milliy.anonymous.milliytravel.enums.ProfileStatus;
import milliy.anonymous.milliytravel.repository.GuideRepository;
import milliy.anonymous.milliytravel.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomProfileDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    private final GuideRepository guideRepository;

    @Override
    public CustomProfileDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        ProfileEntity profileEntity = profileRepository
                .findByPhoneNumberAndDeletedDateIsNull(phone)
                .orElseThrow(() -> new UsernameNotFoundException("Profile Not Found"));

        if (profileEntity.getStatus().equals(ProfileStatus.BLOCK)) {
            log.warn("Profile Blocked phone={}", phone);
            throw new BadCredentialsException("Profile Blocked!, please contact us");
        }

        return new CustomProfileDetails(profileEntity, guideRepository
                .findByProfile_PhoneNumberAndProfile_DeletedDateIsNullAndDeletedDateIsNull(phone)
                .orElse(null));
    }
}
