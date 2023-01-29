package milliy.anonymous.milliytravel.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "trip_facility")
public class TripFacilityEntity extends BaseEntity {

    @Column(name = "facility_id")
    private String facilityId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", insertable = false, updatable = false)
    private FacilityEntity facility;

    @Column(name = "trip_id")
    private String tripId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", insertable = false, updatable = false)
    private TripEntity trip;

}
