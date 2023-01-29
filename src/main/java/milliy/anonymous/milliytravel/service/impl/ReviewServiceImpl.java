package milliy.anonymous.milliytravel.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.config.details.EntityDetails;
import milliy.anonymous.milliytravel.dto.Result;
import milliy.anonymous.milliytravel.dto.request.CommentDTO;
import milliy.anonymous.milliytravel.dto.request.ReviewDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.dto.response.ReviewsResponseDTO;
import milliy.anonymous.milliytravel.entity.GuideEntity;
import milliy.anonymous.milliytravel.entity.ProfileEntity;
import milliy.anonymous.milliytravel.entity.ReviewEntity;
import milliy.anonymous.milliytravel.entity.TripEntity;
import milliy.anonymous.milliytravel.enums.ReviewType;
import milliy.anonymous.milliytravel.repository.GuideRepository;
import milliy.anonymous.milliytravel.repository.ReviewRepository;
import milliy.anonymous.milliytravel.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final ProfileServiceImpl profileService;

    private final GuideRepository guideRepository;

    @Autowired
    @Lazy
    private GuideServiceImpl guideService;

    @Autowired
    @Lazy
    private TripServiceImpl tripService;


    public ActionDTO create(ReviewDTO dto) {

        ProfileEntity profile = EntityDetails.getProfile();

        ReviewEntity entity = new ReviewEntity();

        switch (dto.getType()) {
            case TRIP -> {

                if (Optional.ofNullable(tripService.get(dto.getTripId())).isEmpty()) {
                    log.warn("trip not found! :{}", dto.getTripId());
                    return new ActionDTO(false);
                }

                entity.setTripId(dto.getTripId());
            }
            case GUIDE -> {

                if (Optional.ofNullable(guideService.get(dto.getGuideId())).isEmpty()) {
                    log.warn("guide not found! :{}", dto.getGuideId());
                    return new ActionDTO(false);
                }

                entity.setGuideId(dto.getGuideId());
            }
        }

        entity.setProfileId(profile.getId());
        entity.setRate(dto.getRate());
        entity.setText(dto.getContent());
        entity.setType(dto.getType());

        reviewRepository.save(entity);

        return new ActionDTO(true);
    }

    public ReviewsResponseDTO getReviewsTrip(String tripId, int page, int size) {

        TripEntity tripEntity = tripService.get(tripId);

        if (tripEntity == null) {
            log.warn("trip not found! getTripComment: {}", tripId);
            return null;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<ReviewEntity> entityPage = reviewRepository.getTripReviews(tripId, pageable);

        List<CommentDTO> commentDTOS = entityPage.stream().map(this::toDTO).toList();

        return new ReviewsResponseDTO(commentDTOS, page, size,
                        entityPage.getTotalPages(), entityPage.getTotalElements());

    }

    public List<CommentDTO> getReviewsTrip(String tripId) {

        TripEntity tripEntity = tripService.get(tripId);

        if (tripEntity == null) {
            log.warn("trip not found! getTripComment: {}", tripId);
            return null;
        }
        PageRequest pageble = PageRequest.of(0, 15, Sort.by(Sort.Direction.DESC, "createdDate"));


        Page<ReviewEntity> entityPage = reviewRepository.getTripReviews(tripId, pageble);

        return entityPage.stream().map(this::toDTO).toList();
    }

    public int getReviewsTripCount(String tripId) {

        TripEntity tripEntity = tripService.get(tripId);

        if (tripEntity == null) {
            log.warn("trip not found! getTripComment: {}", tripId);
            return 0;
        }

        return reviewRepository.getTripReviewsCount(tripId);
    }

    public ReviewsResponseDTO getGuideAllComment(int page, int size) {

        ProfileEntity profile = EntityDetails.getProfile();

        Optional<GuideEntity> guideEntity = guideRepository.findByProfile_PhoneNumberAndProfile_DeletedDateIsNullAndDeletedDateIsNull(
                profile.getPhoneNumber());

        if (guideEntity.isEmpty()) {
            log.warn("<< getGuideAllComment guide is null");
            return null;
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<ReviewEntity> entityPage = reviewRepository.getAllReviewByGuideId(guideEntity.get().getId(), pageable);

        List<CommentDTO> commentDTOS = entityPage.stream().map(this::toDTO).toList();

        return new ReviewsResponseDTO(commentDTOS, page, size,
                entityPage.getTotalPages(), entityPage.getTotalElements());

    }

    public ReviewsResponseDTO getTripComment(String guideId, int page, int size) {

        Optional<GuideEntity> guideEntity = guideRepository.findByIdAndDeletedDateIsNull(guideId);
        if (guideEntity.isEmpty()) {
            log.warn("<< getTripComment guide is null");
            return null;
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<ReviewEntity> entityPage = reviewRepository.getTripReviewsByGuideId(guideId, pageable);

        List<CommentDTO> commentDTOS = entityPage.stream().map(this::toDTO).toList();

        return new ReviewsResponseDTO(commentDTOS, page, size,
                        entityPage.getTotalPages(), entityPage.getTotalElements());
    }

    public ReviewsResponseDTO getGuideFeedbacks(String guideId, int page, int size) {

        Optional<GuideEntity> guideEntity = guideRepository.findByIdAndDeletedDateIsNull(guideId);
        if (guideEntity.isEmpty()) {
            log.warn("<< getTripComment guide is null");
            return null;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<ReviewEntity> entityPage = reviewRepository.getFeedbackByGuideId(guideId, pageable);

        List<CommentDTO> commentDTOS = entityPage.stream().map(this::toDTO).toList();

        return new ReviewsResponseDTO(commentDTOS, page, size,
                        entityPage.getTotalPages(), entityPage.getTotalElements());
    }


    public CommentDTO toDTO(ReviewEntity entity) {

        CommentDTO dto = new CommentDTO();

        dto.setRate(entity.getRate());
        dto.setAnswerContent(entity.getAnswerContent());
        dto.setReviewContent(entity.getText());
        dto.setCommentType(entity.getType());
        dto.setReviewTime(entity.getCreatedDate().toString());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());
        dto.setAnswerTime(entity.getAnswerTime().toString());

        dto.setFrom(profileService.toShortInfo(profileService.getById(entity.getProfileId())));

        if (entity.getType().equals(ReviewType.GUIDE)) {
            dto.setGuide(profileService.toShortInfo(profileService.getById(entity.getGuide().getProfileId())));
        } else {
            dto.setTrip(tripService.getShortTrip(entity.getTripId()));
        }

        return dto;
    }

    public ReviewEntity get(String id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public ActionDTO delete(String reviewId) {


        ProfileEntity profileEntity = EntityDetails.getProfile();

        GuideEntity guideEntity = EntityDetails.getGuide();

        if (Optional.ofNullable(guideEntity).isEmpty()) {
            log.warn("Not access {} ", profileEntity);
            return new ActionDTO(false);
        }

        ReviewEntity reviewEntity = get(reviewId);

        if (reviewEntity == null) {
            log.warn("<< delete review is null");
            return new ActionDTO(false);
        }

        switch (reviewEntity.getType()) {

            case TRIP -> {
                if (!reviewEntity.getTrip().getGuideId().equals(guideEntity.getId())) {
                    log.warn("Not Access {} ", profileEntity);
                    return new ActionDTO(false);
                }
            }

            case GUIDE -> {
                if (!reviewEntity.getGuideId().equals(guideEntity.getId())) {
                    log.warn("Not Access {} ", profileEntity);
                    return new ActionDTO(false);
                }
            }
        }

        reviewRepository.updateDeleteDate(LocalDateTime.now(), reviewId);

        return new ActionDTO(true);
    }


    public Boolean isComment(ReviewType type, String profileId, String id) {


        if (type.equals(ReviewType.TRIP)) {
            Long count = reviewRepository.countByTypeAndProfileIdAndTripId(type, profileId, id);
            return count > 0;
        } else if (type.equals(ReviewType.GUIDE)) {
            Long count = reviewRepository.countByTypeAndProfileIdAndGuideId(type, profileId, id);
            return count > 0;
        }

        return false;
    }

}
