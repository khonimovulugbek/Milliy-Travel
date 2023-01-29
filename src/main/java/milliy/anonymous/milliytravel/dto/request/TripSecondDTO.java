package milliy.anonymous.milliytravel.dto.request;

import milliy.anonymous.milliytravel.dto.detail.PriceDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class TripSecondDTO {


    private ArrayList<String> photos;

    private PriceDTO price;

    private ArrayList<FacilityDTO> facilities;

}
