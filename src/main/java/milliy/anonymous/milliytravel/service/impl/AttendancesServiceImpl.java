package milliy.anonymous.milliytravel.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.config.details.EntityDetails;
import milliy.anonymous.milliytravel.dto.attendances.AttendancesRequest;
import milliy.anonymous.milliytravel.dto.attendances.AttendancesResDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.entity.AttendancesEntity;
import milliy.anonymous.milliytravel.entity.ProfileEntity;
import milliy.anonymous.milliytravel.enums.AttendancesStatus;
import milliy.anonymous.milliytravel.enums.AttendancesType;
import milliy.anonymous.milliytravel.repository.AttendancesRepository;
import milliy.anonymous.milliytravel.service.AttendancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttendancesServiceImpl implements AttendancesService {

    private final AttendancesRepository attendancesRepository;

    @Autowired
    @Lazy
    private GuideServiceImpl guideService;


    @Autowired
    @Lazy
    private TripServiceImpl tripService;


    public ActionDTO create(AttendancesResDTO dto) {

        ProfileEntity profile = EntityDetails.getProfile();

        AttendancesEntity entity = new AttendancesEntity();
        entity.setProfileId(profile.getId());

        if (dto.getType().equals(AttendancesType.GUIDE)) {

            if (guideService.get(dto.getId()) == null) {
                log.warn("Guide not found! {}", dto.getId());
                return new ActionDTO("Guide", "Guide not found!", false);
            }

            entity.setGuideId(dto.getId());

        } else {
            if (tripService.get(dto.getId()) == null) {
                log.warn("Trip not found! :{}", dto.getId());
                return new ActionDTO("Trip", "Trip not found!", false);
            }

            entity.setTripId(dto.getId());
        }

        entity.setType(dto.getType());
        entity.setStatus(AttendancesStatus.ACCEPTED);

        attendancesRepository.save(entity);

        return new ActionDTO(true);
    }

    public List<AttendancesRequest> getAttendances(AttendancesResDTO dto) {

        if (dto.getType().equals(AttendancesType.GUIDE)) {

            if (guideService.get(dto.getId()) != null) {
                return attendancesRepository.findByGuideIdAndTypeAndStatus(dto.getId(), AttendancesType.GUIDE, AttendancesStatus.ACCEPTED).
                        stream().map(entity -> new AttendancesRequest(entity.getProfileId())).toList();
            }

            log.warn("guide not found! :{}", dto.getId());

        } else {
            if (tripService.get(dto.getId()) != null) {
                return attendancesRepository.findByTripIdAndTypeAndStatus(dto.getId(), AttendancesType.TRIP, AttendancesStatus.ACCEPTED).
                        stream().
                        map(entity -> new AttendancesRequest(entity.getProfileId())).toList();
            }

            log.warn("trip not found! :{}", dto.getId());

        }
        return new LinkedList<>();
    }


}
