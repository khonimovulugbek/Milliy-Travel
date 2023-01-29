package milliy.anonymous.milliytravel.service.impl;

import milliy.anonymous.milliytravel.dto.Result;
import milliy.anonymous.milliytravel.dto.response.MainResponseDTO;
import milliy.anonymous.milliytravel.service.GuideService;
import milliy.anonymous.milliytravel.service.MainService;
import milliy.anonymous.milliytravel.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final GuideServiceImpl guideService;

    private final TripServiceImpl tripService;


    public MainResponseDTO getMainPage(int page, int size) {

        MainResponseDTO dto = new MainResponseDTO();

        dto.setTopGuides(guideService.getTop10(page, size));

        dto.setTopTrips(tripService.top10(page, size));

        dto.setStatus(true);

        return dto;

    }
}
