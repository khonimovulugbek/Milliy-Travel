package milliy.anonymous.milliytravel.mapper.review;

import milliy.anonymous.milliytravel.dto.response.GuideResponse;
import milliy.anonymous.milliytravel.dto.response.ProfileResponseDTO;
import milliy.anonymous.milliytravel.dto.response.TripResponseDTO;
import milliy.anonymous.milliytravel.enums.ReviewType;


public interface ReviewMapper {

     Integer getRate();

     String getReviewTime();

     String reviewContent();

     ProfileResponseDTO from();

     ReviewType commentType();

     TripResponseDTO trip();

     GuideResponse guide();

     String getAnswerTime();

     String getAnswerContent();
}
