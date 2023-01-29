package milliy.anonymous.milliytravel.entity;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "guide",uniqueConstraints = @UniqueConstraint(columnNames = {"profile_id", "deleted_date"}))
public class GuideEntity extends BaseEntity {


    private String phoneNumber;

    private String biography;

    private Boolean isHiring = false;

    private Double rate = 0d;


    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;


    @Column(name = "price_id")
    private String priceId;
    @OneToOne
    @JoinColumn(name = "price_id",insertable = false,updatable = false)
    private PriceEntity price;

    @Column(name = "profile_id")
    private String profileId;
    @OneToOne
    @JoinColumn(name = "profile_id",insertable = false,updatable = false)
    private ProfileEntity profile;

}
