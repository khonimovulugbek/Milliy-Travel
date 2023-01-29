package milliy.anonymous.milliytravel.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.config.details.EntityDetails;
import milliy.anonymous.milliytravel.dto.request.AnswerDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.entity.GuideEntity;
import milliy.anonymous.milliytravel.entity.ProfileEntity;
import milliy.anonymous.milliytravel.entity.ReviewEntity;
import milliy.anonymous.milliytravel.exception.AppForbiddenException;
import milliy.anonymous.milliytravel.repository.ReviewRepository;
import milliy.anonymous.milliytravel.service.AnswerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerServiceImpl implements AnswerService {

    private final ReviewRepository reviewRepository;

    private final ReviewServiceImpl reviewService;


    public ActionDTO create(AnswerDTO dto) {

        ProfileEntity profileEntity = EntityDetails.getProfile();

        GuideEntity guideEntity = EntityDetails.getGuide();

        if (Optional.ofNullable(guideEntity).isEmpty()) {
            log.warn("Not access {} ", profileEntity);
            return new ActionDTO("Answer", "Not Access", false);
        }

        ReviewEntity reviewEntity = reviewService.get(dto.getReviewId());

        checkById(reviewEntity, guideEntity, profileEntity);

        reviewRepository.setAnswer(dto.getContent(), LocalDateTime.now(), dto.getReviewId());

        return new ActionDTO(true);
    }

    public ActionDTO updateAnswer(AnswerDTO dto) {

        ProfileEntity profileEntity = EntityDetails.getProfile();

        GuideEntity guideEntity = EntityDetails.getGuide();

        if (Optional.ofNullable(guideEntity).isEmpty()) {
            log.warn("Not access {} ", profileEntity);
            return new ActionDTO("Answer", "Not Access", false);
        }

        ReviewEntity reviewEntity = reviewService.get(dto.getReviewId());

        checkById(reviewEntity, guideEntity, profileEntity);

        reviewRepository.setAnswer(dto.getContent(), reviewEntity.getAnswerTime(), dto.getReviewId());

        return new ActionDTO(true);
    }

    public ActionDTO deleteAnswer(String reviewId) {

        ProfileEntity profileEntity = EntityDetails.getProfile();

        GuideEntity guideEntity = EntityDetails.getGuide();

        if (Optional.ofNullable(guideEntity).isEmpty()) {
            log.warn("Not access {} ", profileEntity);
            return new ActionDTO("Answer", "Not Access", false);
        }

        ReviewEntity reviewEntity = reviewService.get(reviewId);

        checkById(reviewEntity, guideEntity, profileEntity);

        Thread thread = new Thread(() -> {
            saveDeletedAnswer(reviewEntity);
        });

        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (th, ex) -> {
            ex.printStackTrace();
        };
        thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);

        thread.start();

        reviewRepository.setAnswer(null, null, reviewId);

        return new ActionDTO(true);
    }

    private void checkById(ReviewEntity reviewEntity, GuideEntity guideEntity, ProfileEntity profileEntity) {

        switch (reviewEntity.getType()) {
            case TRIP -> {
                if (!reviewEntity.getTrip().getGuideId().equals(guideEntity.getId())) {
                    log.warn("Not Access {} ", profileEntity);
                    throw new AppForbiddenException("Not Access!");
                }
            }

            case GUIDE -> {
                if (!reviewEntity.getGuideId().equals(guideEntity.getId())) {
                    log.warn("Not Access {} ", profileEntity);
                    throw new AppForbiddenException("Not Access!");
                }
            }
        }

    }

    private void saveDeletedAnswer(ReviewEntity oldEntity) {
        ReviewEntity entity = new ReviewEntity();
        entity.setAnswerContent(oldEntity.getAnswerContent());
        entity.setAnswerTime(oldEntity.getAnswerTime());
        entity.setRate(oldEntity.getRate());
        entity.setText(oldEntity.getText());
        entity.setType(oldEntity.getType());
        entity.setGuideId(oldEntity.getGuideId());
        entity.setTripId(oldEntity.getTripId());
        entity.setProfileId(oldEntity.getProfileId());
        entity.setReplyId(oldEntity.getReplyId());
        entity.setDeletedDate(LocalDateTime.now());
        reviewRepository.save(entity);
    }

}
