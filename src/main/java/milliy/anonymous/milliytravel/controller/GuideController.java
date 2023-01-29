package milliy.anonymous.milliytravel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.dto.guide.GuideDTO;
import milliy.anonymous.milliytravel.dto.guide.GuideResDTO;
import milliy.anonymous.milliytravel.dto.guide.GuideSearchDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.service.impl.GuideServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static milliy.anonymous.milliytravel.util.Utils.createResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/guide")
public class GuideController {

    private final GuideServiceImpl guideService;

    @Value("${pagination.page}")
    private Integer pageSize;


    @PostMapping("")
    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    public ResponseEntity<GuideDTO> create(@RequestBody @Valid GuideDTO dto) {
        log.info("Create Guide {}", dto);
        GuideDTO guideDTO = guideService.create(dto);
        return guideDTO != null ? ResponseEntity.ok(guideDTO) : ResponseEntity.ok().build();
    }


    @PutMapping("")
    @PreAuthorize("hasAnyRole('GUIDE')")
    public ResponseEntity<ActionDTO> update(@RequestBody @Valid GuideDTO dto) {
        log.info("Update Guide {}", dto);
        ActionDTO actionDTO = guideService.update(dto);
        return actionDTO != null ? ResponseEntity.ok(actionDTO) : ResponseEntity.ok().build();
    }


    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    @GetMapping("/status")
    public ResponseEntity<Boolean> getGuideHiring() {
        log.info("Get Guide Hiring");
        return ResponseEntity.ok(guideService.getGuideHiring());
    }

    @PreAuthorize("hasAnyRole('GUIDE')")
    @PutMapping("/status")
    public ResponseEntity<Boolean> updateIsHiring() {
        log.info("Update Guide Hiring");
        return ResponseEntity.ok(guideService.updateIsHiring());
    }


    @DeleteMapping("")
    @PreAuthorize("hasAnyRole('GUIDE')")
    public ResponseEntity<ActionDTO> delete() {
        log.info("Delete Guide");
        return ResponseEntity.ok(guideService.delete());
    }

    @GetMapping("/{guideId}")
    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    public ResponseEntity<GuideDTO> get(@PathVariable("guideId") String guideId) {
        log.info("Get Guide id={}", guideId);
        GuideDTO guide = guideService.getGuide(guideId);
        return guide != null ? ResponseEntity.ok(guide) : ResponseEntity.ok().build();
    }


    @PostMapping("/search")
    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    public ResponseEntity<GuideResDTO> search(@RequestBody GuideSearchDTO dto,
                                              @RequestParam(value = "page", defaultValue = "1") int page) {
        log.info("Search Guide {}", dto);
        return ResponseEntity.ok(guideService.filter(dto, page - pageSize));
    }


}
