package milliy.anonymous.milliytravel.repository;

import milliy.anonymous.milliytravel.entity.ReviewEntity;
import milliy.anonymous.milliytravel.enums.ReviewType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {


    @Query("select r  from ReviewEntity r where r.guideId = ?1 and r.type = 'TRIP'")
    Page<ReviewEntity> getTripReviewsByGuideId(String guideID, Pageable pageable);

    @Query("select r from ReviewEntity r where r.guideId = ?1 and r.type = 'GUIDE'")
    Page<ReviewEntity> getFeedbackByGuideId(String guideID, Pageable pageable);

    @Query(" select r from ReviewEntity r " +
            "left join r.trip t " +
            "where r.guideId = ?1 or t.guideId = ?1")
    Page<ReviewEntity> getAllReviewByGuideId(String guideId, Pageable pageable);

    @Query("select r from ReviewEntity r  where  r.tripId = ?1 ")
    Page<ReviewEntity> getTripReviews(String tripId, Pageable pageable);

    @Query("select r from ReviewEntity r" +
            " left join r.guide g " +
            " left join r.trip t " +
            " where  r.tripId = ?1 and g.deletedDate is null and t.deletedDate is null")
    List<ReviewEntity> getTripReviews(String tripId);

    @Query("select count(r) from ReviewEntity r  where  r.tripId = ?1 ")
    int getTripReviewsCount(String tripId);

    @Modifying
    @Transactional
    @Query("update ReviewEntity  set answerContent =?1,answerTime = ?2 where id =?3 and deletedDate is null")
    void setAnswer(String answerContent, LocalDateTime answerTime,String reviewId);

    @Modifying
    @Transactional
    @Query("update ReviewEntity set deletedDate = ?1 where id = ?2")
    void updateDeleteDate(LocalDateTime now, String reviewId);

    @Query("select count(r) from ReviewEntity r  where  r.guideId = ?1 ")
    int getGuideReviewsCount(String guideId);

     Long countByTypeAndProfileIdAndTripId(ReviewType type, String profileId, String tripId);
     Long countByTypeAndProfileIdAndGuideId(ReviewType type, String profileId, String tripId);
}

