package milliy.anonymous.milliytravel.dto.attendances;

import milliy.anonymous.milliytravel.enums.AttendancesType;
import lombok.*;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttendancesResDTO {

    @NotBlank(message = "")
    private String id;

    private AttendancesType type;

}
