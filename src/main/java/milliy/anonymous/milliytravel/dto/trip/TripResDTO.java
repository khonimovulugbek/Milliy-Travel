package milliy.anonymous.milliytravel.dto.trip;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TripResDTO {

    private List<?> trips;

    private Integer currentPageNumber;

    private Integer currentPageItems;

    private Integer totalPage;

    private Long totalItems;

    public TripResDTO(List<?> trips, Integer currentPageNumber, Integer currentPageItems) {
        this.trips = trips;
        this.totalPage = currentPageNumber;
        this.totalItems = Long.valueOf(currentPageItems);
    }
}
