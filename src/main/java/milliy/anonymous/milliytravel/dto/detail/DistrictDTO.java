package milliy.anonymous.milliytravel.dto.detail;

import milliy.anonymous.milliytravel.enums.Region;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor

public class DistrictDTO {

    private String name;
    private Region region;

}
