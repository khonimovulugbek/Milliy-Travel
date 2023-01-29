package milliy.anonymous.milliytravel.service;

import milliy.anonymous.milliytravel.dto.attendances.AttendancesRequest;
import milliy.anonymous.milliytravel.dto.attendances.AttendancesResDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;

import java.util.List;


public interface AttendancesService {

    ActionDTO create(AttendancesResDTO dto);

    List<AttendancesRequest> getAttendances(AttendancesResDTO dto);

}
