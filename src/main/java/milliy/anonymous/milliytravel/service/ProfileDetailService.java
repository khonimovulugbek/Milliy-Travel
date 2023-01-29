package milliy.anonymous.milliytravel.service;

import milliy.anonymous.milliytravel.dto.profile.ProfileDetailDTO;
import milliy.anonymous.milliytravel.entity.ProfileDetailEntity;


public interface ProfileDetailService {

     void create(ProfileDetailDTO dto);

     ProfileDetailEntity getByPhoneNumber(String phoneNumber);

     void delete(String id);
}
