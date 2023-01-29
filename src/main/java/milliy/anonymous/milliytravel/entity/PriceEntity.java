package milliy.anonymous.milliytravel.entity;


import milliy.anonymous.milliytravel.enums.Currency;
import milliy.anonymous.milliytravel.enums.TourType;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "price")
@Getter
@Setter
public class PriceEntity extends BaseEntity {

    @Column(name = "cost")
    private Long cost;

    @Column
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column
    @Enumerated(EnumType.STRING)
    private TourType type;

}