package milliy.anonymous.milliytravel.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.dto.detail.LocationDTO;
import milliy.anonymous.milliytravel.dto.request.FacilityDTO;
import milliy.anonymous.milliytravel.dto.request.TripFirstDTO;
import milliy.anonymous.milliytravel.dto.request.TripSecondDTO;
import milliy.anonymous.milliytravel.dto.request.TripUploadPhotoDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.dto.trip.*;
import milliy.anonymous.milliytravel.service.TripLocationService;
import milliy.anonymous.milliytravel.service.TripPhotoService;
import milliy.anonymous.milliytravel.service.impl.TripServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static milliy.anonymous.milliytravel.util.Utils.createResponse;


@RestController
@RequestMapping("/api/v1/trip")
@Slf4j
@RequiredArgsConstructor

public class TripController {

    private final TripServiceImpl tripService;

    private final TripPhotoService tripPhotoService;

    private final TripLocationService tripLocationService;

    @Value("${pagination.page}")
    private Integer pageSize;


    @PreAuthorize("hasAnyRole('GUIDE')")
    @PostMapping("")
    public ResponseEntity<TripFirstDTO> create(@RequestBody @Valid TripFirstDTO dto) {
        log.info("Create Trip {}", dto);
        TripFirstDTO tripFirstDTO = tripService.create(dto);
        return tripFirstDTO != null ? ResponseEntity.ok(tripFirstDTO) : ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('GUIDE')")
    @PutMapping("/update/{tripId}")
    public ResponseEntity<ActionDTO> update(@RequestBody @Valid TripFirstDTO dto,
                                            @PathVariable("tripId") String tripId) {
        log.info("Update Trip {}", dto);
        return ResponseEntity.ok(tripService.update(dto, tripId));
    }

    @PutMapping("/photo/{tripId}")
    @PreAuthorize("hasAnyRole('GUIDE')")
    public ResponseEntity<ActionDTO> secondSend(@PathVariable("tripId") String tripId,
                                                @RequestBody TripSecondDTO dto) {
        log.info("Update Trip {}", dto);
        return ResponseEntity.ok(tripPhotoService.create(tripId, dto));
    }


    @PreAuthorize("hasAnyRole('GUIDE')")
    @PostMapping("/trip-upload")
    public ResponseEntity<TripUploadPhotoDTO> tripUpload(@RequestBody TripUploadPhotoDTO dto) {
        log.info("Upload Trip Photo {}", dto);
        return ResponseEntity.ok(tripLocationService.tripUploadPhoto(dto));
    }


    @GetMapping("")
    @PreAuthorize("hasAnyRole('GUIDE')")
    public ResponseEntity<PageImpl<TripShortInfoDTO>> getTrip(@RequestParam(value = "page", defaultValue = "1") int page,
                                                              @RequestParam(value = "size", defaultValue = "15") int size) {
        log.info("Get Trip");
        return ResponseEntity.ok(tripService.getTrip(page - pageSize, size));
    }


    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    @PostMapping("/search")
    public ResponseEntity<TripResDTO> filter(@RequestBody SearchTripDTO dto,
                                             @RequestParam(value = "page", defaultValue = "1") int page) {
        log.info("Search By Filter {}", dto);
        return ResponseEntity.ok(tripService.filter(dto, page - pageSize));
    }


    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    @GetMapping("/{guideId}/trips")
    public ResponseEntity<TripResDTO> getTripByGuideId(@PathVariable("guideId") String guideId,
                                                       @RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "15") int size) {
        log.info("Get Trip By Guide Id {}", guideId);
        return ResponseEntity.ok(tripService.getTrips(guideId, page - pageSize, size));
    }

    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    @GetMapping("/{tripId}")
    public ResponseEntity<TripDTO> getTrip(@PathVariable("tripId") String tripId) {
        log.info("Get Trip By Id {}", tripId);
        TripDTO trip = tripService.getTripById(tripId);
        return trip != null ? ResponseEntity.ok(trip) : ResponseEntity.ok().build();
    }


    @PreAuthorize("hasAnyRole('GUIDE')")
    @DeleteMapping("/{tripId}")
    public ResponseEntity<ActionDTO> delete(@PathVariable("tripId") String tripId) {
        log.info("Delete Trip tripId={}", tripId);
        return ResponseEntity.ok(tripService.deleteTrip(tripId));
    }


    @PreAuthorize("hasAnyRole('GUIDE')")
    @GetMapping("/trip-photo/{tripId}")
    public ResponseEntity<List<TripPhotoResDTO>> tripPhotoList(@PathVariable("tripId") String tripId) {
        log.info("Trip Photo List tripId={}", tripId);
        return ResponseEntity.ok(tripService.tripPhotoList(tripId));
    }

    @PreAuthorize("hasAnyRole('GUIDE')")
    @GetMapping("/trip-location/{tripId}")
    public ResponseEntity<List<LocationDTO>> tripLocationList(@PathVariable("tripId") String tripId) {
        log.info("Trip Location List tripId={}", tripId);
        return ResponseEntity.ok(tripService.tripLocationList(tripId));
    }

    @PreAuthorize("hasAnyRole('GUIDE')")
    @GetMapping("/trip-facility/{tripId}")
    public ResponseEntity<List<FacilityDTO>> tripFacilityList(@PathVariable("tripId") String tripId) {
        log.info("Trip Facility List tripId={}", tripId);
        return ResponseEntity.ok(tripService.tripFacilityList(tripId));
    }


}
