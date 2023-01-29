package milliy.anonymous.milliytravel.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "answer")
@Getter
@Setter
public class AnswerEntity extends BaseEntity {

    @Column(columnDefinition = "text")
    private String content;


    @Column(name = "review_id")
    private String reviewId;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id",insertable = false,updatable = false)
    private ReviewEntity review;



    @Column(name = "guide_id")
    private String guideId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id",updatable = false,insertable = false)
    private GuideEntity guide;



}
