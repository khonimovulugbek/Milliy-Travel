package milliy.anonymous.milliytravel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.config.details.EntityDetails;
import milliy.anonymous.milliytravel.dto.detail.AttachDTO;
import milliy.anonymous.milliytravel.dto.profile.ProfileDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.enums.AppLang;
import milliy.anonymous.milliytravel.service.impl.ProfileServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static milliy.anonymous.milliytravel.util.Utils.createResponse;

@Slf4j
@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileServiceImpl profileService;

    @Value("${pagination.page}")
    private Integer pageSize;


    @PutMapping("")
    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    public ResponseEntity<ProfileDTO> update(@RequestBody @Valid ProfileDTO dto) {
        log.info("Update Profile {}", dto);
        return ResponseEntity.ok(profileService.update(dto));
    }


    @PutMapping("/photo")
    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    public ResponseEntity<AttachDTO> uploadPhoto(@RequestParam MultipartFile file) {
        log.info("Upload Main Photo To Profile");
        return ResponseEntity.ok(profileService.uploadPhoto(file));
    }

    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    @PutMapping("/status")
    public ResponseEntity<Boolean> changeStatus() {
        log.info("Change Profile Status ");
        return ResponseEntity.ok(profileService.changeStatus());
    }

    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    @GetMapping("")
    public ResponseEntity<PageImpl<ProfileDTO>>
    profilePaginationList(@RequestParam(value = "page", defaultValue = "1") int page,
                          @RequestParam(value = "size", defaultValue = "5") int size) {
        log.info("Profile Pagination List page={} size={}", page, size);
        return ResponseEntity.ok(profileService.profilePaginationList(page - pageSize, size));
    }

    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileDTO> getById(@PathVariable("profileId") String profileId) {
        log.info("Get Profile id={}", profileId);
        return ResponseEntity.ok(profileService.get(profileId));
    }

    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    @GetMapping("/language")
    public ResponseEntity<AppLang> getLanguageById() {
        log.info("Get Profile App Language");
        return ResponseEntity.ok(profileService.getByPhoneNumber(EntityDetails.getProfile().getPhoneNumber()).getAppLanguage());
    }

    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    @PutMapping("/language")
    public ResponseEntity<ActionDTO> updateLanguageById(@RequestParam("lang") AppLang lang) {
        log.info("Put Profile Language lang={}", lang);
        return ResponseEntity.ok(profileService.updateLanguage(lang));
    }

    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    @DeleteMapping("")
    public ResponseEntity<ActionDTO> delete() {
        log.info("Delete Profile profile={}", EntityDetails.getProfile());
        return ResponseEntity.ok(profileService.delete());
    }

}
