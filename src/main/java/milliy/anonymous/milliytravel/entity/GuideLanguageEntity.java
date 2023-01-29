package milliy.anonymous.milliytravel.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "guide_language")
@Getter
@Setter
public class GuideLanguageEntity extends BaseEntity {


    @Column(name = "language_id")
    private String languageId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id",insertable = false,updatable = false)
    private LanguageEntity language;


    @Column(name = "guide_id")
    private String guideId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id",insertable = false,updatable = false)
    private GuideEntity guide;
}
