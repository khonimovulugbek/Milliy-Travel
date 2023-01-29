package milliy.anonymous.milliytravel.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.enums.Region;
import milliy.anonymous.milliytravel.service.DistrictService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static milliy.anonymous.milliytravel.util.Utils.createResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/district")
@Slf4j
public class DistrictController {

    private final DistrictService districtService;


    @GetMapping("/{region}")
    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    public ResponseEntity<List<String>> getByRegion(@PathVariable("region") Region region){
        log.info("Get District By Region Name {}",region);
        return ResponseEntity.ok(districtService.getByRegion(region));
    }


}

