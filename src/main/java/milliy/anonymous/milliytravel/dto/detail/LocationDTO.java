package milliy.anonymous.milliytravel.dto.detail;

import milliy.anonymous.milliytravel.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public class LocationDTO extends BaseDTO {

    private String provence;

    private String district;

    private String description;
    
}
