package milliy.anonymous.milliytravel.dto.request;

import milliy.anonymous.milliytravel.dto.BaseDTO;
import milliy.anonymous.milliytravel.dto.detail.LocationDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@ToString
public class TripFirstDTO extends BaseDTO {

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Desc cannot be null")
    private String desc;

    @NotNull(message = "MaxPeople cannot be null")
    @Positive(message = "MaxPeople can be positive number")
    private Integer maxPeople;

    @NotNull(message = "MinPeople cannot be null")
    @Positive(message = "MinPeople can be positive number")
    private Integer minPeople;

    @NotNull(message = "Days cannot be null")
    @Min(value = 1, message = "Days can be between 1 and 99")
    @Max(value = 99, message = "Days can be between 1 and 99")
    private Integer days;

    private List<LocationDTO> travelLocations;

}
