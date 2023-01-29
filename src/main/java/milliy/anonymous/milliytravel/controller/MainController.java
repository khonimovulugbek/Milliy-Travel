package milliy.anonymous.milliytravel.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.dto.response.MainResponseDTO;
import milliy.anonymous.milliytravel.service.MainService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static milliy.anonymous.milliytravel.util.Utils.createResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/main")
@Slf4j
public class MainController {

    private final MainService mainService;

    @Value("${pagination.page}")
    private Integer pageSize;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    public ResponseEntity<MainResponseDTO> getMainPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Main Page");
        return ResponseEntity.ok(mainService.getMainPage(page - pageSize, size));
    }
}
