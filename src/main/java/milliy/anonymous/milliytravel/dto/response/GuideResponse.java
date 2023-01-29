package milliy.anonymous.milliytravel.dto.response;


import milliy.anonymous.milliytravel.dto.*;
import milliy.anonymous.milliytravel.dto.detail.LanguageDTO;
import milliy.anonymous.milliytravel.dto.detail.LocationDTO;
import milliy.anonymous.milliytravel.dto.detail.PriceDTO;
import milliy.anonymous.milliytravel.dto.request.CommentDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@NoArgsConstructor
public class GuideResponse extends BaseDTO {

    private String profileId;

    private ProfileResponseDTO profile;

    private String secondPhoneNumber;

    private String biography;

    private Boolean isHiring;

    private double rate;

    private PriceDTO price;

    private List<LanguageDTO> languages;

    private List<LocationDTO> travelLocations;

    private List<CommentDTO> reviews;

    private String attachId;

}
