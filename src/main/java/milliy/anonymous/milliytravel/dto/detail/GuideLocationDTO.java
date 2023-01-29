package milliy.anonymous.milliytravel.dto.detail;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GuideLocationDTO {

    private  String id;

    private  LocalDateTime createdDate;

    private  LocalDateTime updatedDate;

    private  String locationId;

    private  String guideId;

    public GuideLocationDTO(String locationId, String guideId) {
        this.locationId = locationId;
        this.guideId = guideId;
    }
}
