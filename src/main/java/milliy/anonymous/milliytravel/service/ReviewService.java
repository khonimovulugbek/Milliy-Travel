package milliy.anonymous.milliytravel.service;

import milliy.anonymous.milliytravel.dto.Result;
import milliy.anonymous.milliytravel.dto.request.CommentDTO;
import milliy.anonymous.milliytravel.dto.request.ReviewDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.dto.response.ReviewsResponseDTO;
import milliy.anonymous.milliytravel.entity.ReviewEntity;
import milliy.anonymous.milliytravel.enums.ReviewType;

import java.util.List;


public interface ReviewService {

    ActionDTO create(ReviewDTO dto);

    ReviewsResponseDTO getReviewsTrip(String tripId, int page, int size);

    List<CommentDTO> getReviewsTrip(String tripId);

    ReviewsResponseDTO getGuideAllComment(int page, int size);

    ReviewsResponseDTO getTripComment(String guideId, int page, int size);

    ReviewsResponseDTO getGuideFeedbacks(String guideId, int page, int size);

    ActionDTO delete(String reviewId);

}
