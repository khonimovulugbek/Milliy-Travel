package milliy.anonymous.milliytravel.config.details;

import milliy.anonymous.milliytravel.entity.GuideEntity;
import milliy.anonymous.milliytravel.entity.ProfileEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class EntityDetails {

    private static CustomProfileDetails getEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomProfileDetails) authentication.getPrincipal();
    }

    public static ProfileEntity getProfile() {
        return getEntity().getProfile();
    }

    public static GuideEntity getGuide() {
        return getEntity().getGuide();
    }


}
