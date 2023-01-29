package milliy.anonymous.milliytravel.dto.guide;

import milliy.anonymous.milliytravel.annotation.SecondValidPhone;
import milliy.anonymous.milliytravel.dto.BaseDTO;
import milliy.anonymous.milliytravel.dto.attendances.AttendancesRequest;
import milliy.anonymous.milliytravel.dto.detail.LanguageDTO;
import milliy.anonymous.milliytravel.dto.detail.LocationDTO;
import milliy.anonymous.milliytravel.dto.detail.PriceDTO;
import milliy.anonymous.milliytravel.dto.profile.ProfileDTO;
import milliy.anonymous.milliytravel.dto.request.CommentDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor

public class GuideDTO extends BaseDTO {

    private String profileId;

    @SecondValidPhone(message = "SecondPhoneNumber required")
    private String secondPhoneNumber;

    private String biography;

    private Boolean isHiring;

    private double rate;

    private PriceDTO price;

    private List<LanguageDTO> languages;

    private List<LocationDTO> travelLocations;

    private String attachId;

    private ProfileDTO profile;

    private List<AttendancesRequest> attendances;

    private List<CommentDTO> reviews;

    private Boolean isComment;

    public GuideDTO(String id, String secondPhoneNumber, String biography, Boolean isHiring, double rate, ProfileDTO profileDTO) {
        super.id = id;
        this.secondPhoneNumber = secondPhoneNumber;
        this.biography = biography;
        this.isHiring = isHiring;
        this.rate = rate;
        this.profile = profileDTO;
    }
}
