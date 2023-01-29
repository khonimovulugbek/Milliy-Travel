package milliy.anonymous.milliytravel.dto.trip;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor

public class TripFilterResultDTO {

    private List<TripShortInfoDTO> resultList;

    private Long maxResults;
}
