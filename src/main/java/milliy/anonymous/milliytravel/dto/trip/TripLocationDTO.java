package milliy.anonymous.milliytravel.dto.trip;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TripLocationDTO {

    private  String id;

    private  LocalDateTime createdDate;

    private  LocalDateTime updatedDate;

    private   String photo;

    private  String locationId;

    private  String tripId;
}
