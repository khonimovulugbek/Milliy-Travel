package milliy.anonymous.milliytravel.service;

import milliy.anonymous.milliytravel.dto.Result;
import milliy.anonymous.milliytravel.dto.profile.ProfileDTO;
import milliy.anonymous.milliytravel.dto.profile.ProfileDetailDTO;
import milliy.anonymous.milliytravel.dto.response.LoginResponse;
import milliy.anonymous.milliytravel.dto.response.RegisterResponse;
import org.springframework.http.ResponseEntity;


public interface AuthorizationService {

     ResponseEntity<LoginResponse> sendSms(ProfileDetailDTO dto);

     ResponseEntity<LoginResponse> login(ProfileDetailDTO dto);

     ResponseEntity<RegisterResponse> registration(ProfileDTO dto);

     ResponseEntity<?> delete(String id);
}
