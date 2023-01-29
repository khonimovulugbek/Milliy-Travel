package milliy.anonymous.milliytravel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.dto.attendances.AttendancesRequest;
import milliy.anonymous.milliytravel.dto.attendances.AttendancesResDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.service.AttendancesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendances")
public class AttendancesController {

    private final AttendancesService attendancesService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    public ResponseEntity<ActionDTO> create(@RequestBody @Valid AttendancesResDTO dto) {
        log.info("Create attendances {}",dto);
        return ResponseEntity.ok(attendancesService.create(dto));
    }


    @GetMapping("")
    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    public ResponseEntity<List<AttendancesRequest>> getAttendances(@RequestBody @Valid AttendancesResDTO dto) {
        log.info("Get Attendances {}",dto);
        return ResponseEntity.ok(attendancesService.getAttendances(dto));
    }

}
