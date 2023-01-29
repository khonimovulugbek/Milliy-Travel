package milliy.anonymous.milliytravel.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.dto.Result;
import milliy.anonymous.milliytravel.dto.profile.ProfileDTO;
import milliy.anonymous.milliytravel.dto.profile.ProfileDetailDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.dto.response.LoginResponse;
import milliy.anonymous.milliytravel.dto.response.RegisterResponse;
import milliy.anonymous.milliytravel.entity.ProfileDetailEntity;
import milliy.anonymous.milliytravel.enums.ProfileStatus;
import milliy.anonymous.milliytravel.mapper.GuideProfileInfoMapper;
import milliy.anonymous.milliytravel.service.AuthorizationService;
import milliy.anonymous.milliytravel.service.DeviceService;
import milliy.anonymous.milliytravel.service.GuideService;
import milliy.anonymous.milliytravel.service.ProfileDetailService;
import milliy.anonymous.milliytravel.util.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final ProfileServiceImpl profileService;

    private final GuideService guideService;

    private final DeviceService deviceService;

    private final ProfileDetailService profileDetailService;

    @Value("${message.access.key:NO_KEY}")
    private String ACCESS_KEY;

    public ResponseEntity<LoginResponse> sendSms(ProfileDetailDTO dto) {
//        String code = RandomUtil.getRandomSmsCode();
        String code = "1111";
        log.info("sendSms code=" + code);

        dto.setSmsCode(code);
        dto.setSms("Your verification code is " + code);

//        try {
//            sendMessage(dto);
//        } catch (RuntimeException e) {
//            log.warn("Bad request {}", dto);
//            return response(dto, new LoginResponse());
//        }

        profileDetailService.create(dto);

        return ResponseEntity.ok(new LoginResponse(true, null, code));
    }

//    public void sendMessage(ProfileDetailDTO dto) {
//        JSONObject jsonObject = new JSONObject();
//
//        String phone = dto.getPhoneNumber();
//
//        jsonObject.put("recipients",phone);
//        jsonObject.put("originator", "Caravan");
//        jsonObject.put("body", dto.getSms());
//
//        RequestBody formBody = RequestBody.create(
//                jsonObject.toString(),
//                MediaType.parse("application/json; charset=utf-8"));
//
//        Request request = new Request.Builder()
//                .url("https://rest.messagebird.com/messages")
//                .header("Accept", "application/json")
//                .header("Content-type", "application/json")
//                .header("Authorization", "AccessKey " + ACCESS_KEY)
//                .post(formBody)
//                .build();
//
//        OkHttpClient client = new OkHttpClient();
//
//        Thread thread = new Thread(() -> {
//            try {
//                Response response = client.newCall(request).execute();
//                if (response.isSuccessful()) {
//                    System.out.println(response);
//                } else {
//                    throw new RuntimeException();
//                }
//            } catch (IOException e) {
//                throw new RuntimeException();
//            }
//        });
//
//        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (th, ex) -> {
//            ex.printStackTrace();
//        };
//        thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
//
//        thread.start();
//
//    }

    public ResponseEntity<LoginResponse> login(ProfileDetailDTO dto) {

        dto.setPhoneNumber(dto.getPhoneNumber());

        ProfileDetailEntity entity = profileDetailService.getByPhoneNumber(dto.getPhoneNumber());

        GuideProfileInfoMapper mapper = profileService.getByPhoneNumberMapper(dto.getPhoneNumber());

        if (Optional.ofNullable(entity).isEmpty() ||
                !entity.getSmsCode().equals(dto.getSmsCode()) ||
                LocalDateTime.now().isAfter(entity.getUpdatedDate().plusMinutes(ProfileDetailEntity.TTL))) {
            log.warn("Bad request {}", dto);
            return ResponseEntity.ok(response(dto,
                    new LoginResponse(false, false, null, null)));
        }

        deviceService.create(dto.getDevice(), entity.getId());

        if (Optional.ofNullable(mapper).isPresent()) {
            ProfileDTO profileDTO = profileService.toDTOMapper(mapper);

            profileDTO.setToken(JwtUtils.encode(profileDTO.getPhoneNumber()));


            if (profileDTO.getStatus().equals(ProfileStatus.BLOCK)) {
                LoginResponse loginResponse = new LoginResponse();
                switch (profileDTO.getAppLanguage()) {
                    case en -> {
                        loginResponse.setTitle("You are not allowed!");
                        loginResponse.setMessage("Your account is blocked.");
                    }
                    case ru -> {
                        loginResponse.setTitle("Не разрешено!");
                        loginResponse.setMessage("Ваш аккаунт заблокирован.");
                    }
                    case uz -> {
                        loginResponse.setTitle("Sizga ruxsat berilmagan!");
                        loginResponse.setMessage("Sizning accountingiz blocklangan!");
                    }
                }
                loginResponse.setStatus(false);
                return ResponseEntity.ok(loginResponse);
            }

            switch (profileDTO.getRole()) {
                case ROLE_TOURIST -> {
                    return ResponseEntity.ok(new LoginResponse(true, false, profileDTO, null));
                }
                case ROLE_GUIDE -> {
                    return ResponseEntity.ok(new LoginResponse(true, true, profileDTO, profileDTO.getGuideId()));
                }
            }
        }

        return ResponseEntity.ok(new LoginResponse(false, false, null, null));
    }

    /*    public ResponseEntity<RegisterResponse> registration(ProfileDTO dto) {
            RegisterResponse response = new RegisterResponse();

            ProfileDetailEntity entity = profileDetailService.getByPhoneNumber(dto.getPhoneNumber());

            if (Optional.ofNullable(entity).isEmpty()) {
                response.setMessage("You're not login!");
                return ResponseEntity.badRequest().body(response);
            }
            if (Optional.ofNullable(profileService.getByPhoneNumber(dto.getPhoneNumber())).isPresent()) {
                response.setMessage("This phone number already used!");
                return ResponseEntity.badRequest().body(response);
            }

            return ResponseEntity.ok(new RegisterResponse(true));
        }*/


    public ResponseEntity<RegisterResponse> registration(ProfileDTO dto) {
        RegisterResponse response = new RegisterResponse();

        dto.setPhoneNumber(dto.getPhoneNumber());

        ProfileDetailEntity entity = profileDetailService.getByPhoneNumber(dto.getPhoneNumber());


        if (Optional.ofNullable(entity).isEmpty()) {
            response.setMessage("You're not login!");
            return ResponseEntity.ok(response);
        }
        if (Optional.ofNullable(profileService.getByPhoneNumber(dto.getPhoneNumber())).isPresent()) {
            response.setMessage("This phone number already used!");
            return ResponseEntity.ok(response);
        }

        dto.setAppLanguage(entity.getAppLanguage());
        ActionDTO action = profileService.create(dto);
        if (!action.getStatus()) {
            response.setMessage(action.getMessage());
            return ResponseEntity.ok(response);
        }

        var profileEntity = profileService.getByPhoneNumber(dto.getPhoneNumber());
        var profile = profileService.toDTO(profileEntity);
        profile.setToken(JwtUtils.encode(profile.getPhoneNumber()));

        return ResponseEntity.ok(new RegisterResponse(true, profile));
    }

    public LoginResponse response(ProfileDetailDTO dto, LoginResponse response) {

        switch (dto.getAppLanguage()) {
            case en -> {
                response.setTitle("Sms code incorrect");
                response.setMessage("You entered wrong code. Please try again. If the code didn't come. Please contact us.");
            }
            case uz -> {
                response.setTitle("Sms kod xato terilgan");
                response.setMessage("Siz xato kod terdingiz. Iltimos qaytadan urinib ko'ring. Agar kod kelmagan bo'lsa. Biz bilan bog'laning.");
            }
            case ru -> {
                response.setTitle("Не правильный смс код");
                response.setMessage("Вы ввели не правильный смс код. Пожалуйста повторите ещё раз. Если код не пришёл. Свяжитесь с нами.");
            }
        }
        return response;
    }

/*    public ResponseEntity<?> delete(Long id) {
        profileService.delete(id);
        return ResponseEntity.ok().build();
    }*/

    public ResponseEntity<?> delete(String id) {
        profileDetailService.delete(id);
        return ResponseEntity.ok().build();
    }
}
