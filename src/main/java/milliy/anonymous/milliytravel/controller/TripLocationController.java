package milliy.anonymous.milliytravel.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.service.TripLocationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/trip-location")
@RequiredArgsConstructor

public class TripLocationController {

    private final TripLocationService tripLocationService;


}
