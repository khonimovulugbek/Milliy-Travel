package milliy.anonymous.milliytravel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.dto.request.AnswerDTO;
import milliy.anonymous.milliytravel.dto.request.ReviewDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.dto.response.ReviewsResponseDTO;
import milliy.anonymous.milliytravel.service.AnswerService;
import milliy.anonymous.milliytravel.service.ReviewService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static milliy.anonymous.milliytravel.util.Utils.createResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    private final AnswerService answerService;

    @Value("${pagination.page}")
    private Integer pageSize;

    @PostMapping("")
    public ResponseEntity<ActionDTO> create(@RequestBody @Valid ReviewDTO dto) {
        log.info("Create Comment {}", dto);
        return ResponseEntity.ok(reviewService.create(dto));
    }


    @PostMapping("/answer")
    @PreAuthorize("hasRole('GUIDE')")
    public ResponseEntity<ActionDTO> answer(@RequestBody @Valid AnswerDTO dto) {
        log.info("Answer {}", dto);
        return ResponseEntity.ok(answerService.create(dto));
    }


    @DeleteMapping("/review-delete/{reviewId}")
    @PreAuthorize("hasRole('GUIDE')")
    public ResponseEntity<ActionDTO> reviewDelete(@PathVariable("reviewId") String reviewId) {
        log.info("Review delete reviewId = {}", reviewId);
        return ResponseEntity.ok(reviewService.delete(reviewId));
    }


    @DeleteMapping("/answer-delete/{reviewId}")
    @PreAuthorize("hasRole('GUIDE')")
    public ResponseEntity<ActionDTO> answerDelete(@PathVariable("reviewId") String reviewId) {
        log.info("Answer delete reviewId = {}", reviewId);
        return ResponseEntity.ok(answerService.deleteAnswer(reviewId));
    }


    @PutMapping("/answer-update")
    @PreAuthorize("hasRole('GUIDE')")
    public ResponseEntity<ActionDTO> answerUpdate(@RequestBody @Valid AnswerDTO dto) {
        log.info("Answer Update {}", dto);
        return ResponseEntity.ok(answerService.updateAnswer(dto));
    }


    @GetMapping("/{guideId}/guide-trip")
    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    public ResponseEntity<ReviewsResponseDTO> tripReviewsGuide(@PathVariable("guideId") String guideId,
                                                               @RequestParam(value = "page", defaultValue = "1") int page,
                                                               @RequestParam(value = "size", defaultValue = "15") int size) {
        log.info("Get Trip Comments By Guide Id {}", guideId);
        ReviewsResponseDTO tripComment = reviewService.getTripComment(guideId, page - pageSize, size);
        return tripComment != null ? ResponseEntity.ok(tripComment) : ResponseEntity.ok().build();
    }


    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('GUIDE')")
    public ResponseEntity<ReviewsResponseDTO> allReviewsGuide(@RequestParam(value = "page", defaultValue = "1") int page,
                                                              @RequestParam(value = "size", defaultValue = "15") int size) {
        log.info("Get All Comments");
        ReviewsResponseDTO guideAllComment = reviewService.getGuideAllComment(page - pageSize, size);
        return guideAllComment != null ? ResponseEntity.ok(guideAllComment) : ResponseEntity.ok().build();
    }


    @GetMapping("/{guideId}/guide-feedback")
    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    public ResponseEntity<ReviewsResponseDTO> reviewsGuide(@PathVariable("guideId") String guideId,
                                                           @RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "size", defaultValue = "15") int size) {
        log.info("Get Feedback Comments By Guide Id {}", guideId);
        return ResponseEntity.ok(reviewService.getGuideFeedbacks(guideId, page - pageSize, size));
    }

    @GetMapping("/trip-review/{tripId}")
    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    public ResponseEntity<ReviewsResponseDTO> tripReviews(@PathVariable("tripId") String tripId,
                                                          @RequestParam(value = "page", defaultValue = "1") int page,
                                                          @RequestParam(value = "size", defaultValue = "15") int size) {
        log.info("Get Trip Comments By Trip Id {}", tripId);
        ReviewsResponseDTO reviewsTrip = reviewService.getReviewsTrip(tripId, page - pageSize, size);
        return reviewsTrip != null ? ResponseEntity.ok(reviewsTrip) : ResponseEntity.ok().build();
    }


}
