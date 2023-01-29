package milliy.anonymous.milliytravel.entity;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trip")
@Getter
@Setter

public class TripEntity extends BaseEntity {


    private String name;

    private String description;

    private Integer minPeople;

    private Integer maxPeople;

    @Column(name = "price_id")
    private String priceId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_id", insertable = false, updatable = false)
    private PriceEntity price;

    @Column(name = "guide_id")
    private String guideId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id", insertable = false, updatable = false)
    private GuideEntity guide;

    private String phoneNumber;

    private Double rate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    private Integer days;



}
