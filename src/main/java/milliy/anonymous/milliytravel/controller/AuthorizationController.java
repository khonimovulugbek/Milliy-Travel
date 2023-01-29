package milliy.anonymous.milliytravel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.dto.profile.ProfileDTO;
import milliy.anonymous.milliytravel.dto.profile.ProfileDetailDTO;
import milliy.anonymous.milliytravel.dto.response.LoginResponse;
import milliy.anonymous.milliytravel.dto.response.RegisterResponse;
import milliy.anonymous.milliytravel.service.AuthorizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static milliy.anonymous.milliytravel.util.Utils.createResponse;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;


    @PostMapping("/send")
    public ResponseEntity<LoginResponse> sendSms(@RequestBody @Valid ProfileDetailDTO dto) {
        log.info("Send Sms {}", dto);
        return authorizationService.sendSms(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid ProfileDetailDTO dto) {
        log.info("Login {}", dto);
        return authorizationService.login(dto);
    }

    @PostMapping("/registration")
    public ResponseEntity<RegisterResponse> registration(@RequestBody @Valid ProfileDTO dto) {
        log.info("Registration {}", dto);
        return authorizationService.registration(dto);
    }

//    @ApiOperation(value = "Delete", notes = "Method used for delete profile")
//    @DeleteMapping("/delete")
//    public ResponseEntity<?> delete(UserDetails userDetails) {
//        log.info("Delete {}", userDetails.getUsername());
//        return authorizationService.delete(userDetails.getUsername());
//    }
}
