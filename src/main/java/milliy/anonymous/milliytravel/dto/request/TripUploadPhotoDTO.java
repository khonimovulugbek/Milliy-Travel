package milliy.anonymous.milliytravel.dto.request;

import milliy.anonymous.milliytravel.dto.detail.LocationDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TripUploadPhotoDTO {

    private String id;

    private String photoId;

    private LocationDTO location;
}
