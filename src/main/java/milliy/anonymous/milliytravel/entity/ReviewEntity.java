package milliy.anonymous.milliytravel.entity;

import milliy.anonymous.milliytravel.enums.ReviewType;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "review")
public class ReviewEntity extends BaseEntity {


    @Column
    private String text;

    private Integer rate;

    @Enumerated(EnumType.STRING)
    private ReviewType type;

    @Column(name = "profile_id")
    private String profileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;


    @Column(name = "guide_id")
    private String guideId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id", insertable = false, updatable = false)
    private GuideEntity guide;


    @Column(name = "reply_id")
    private String replyId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id", insertable = false, updatable = false)
    private ReviewEntity reply;


    @Column(name = "trip_id")
    private String tripId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", insertable = false, updatable = false)
    private TripEntity trip;

    private LocalDateTime answerTime;

    private String answerContent;

    private LocalDateTime deletedDate;


}
