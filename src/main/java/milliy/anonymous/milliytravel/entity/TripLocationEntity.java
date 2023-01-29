package milliy.anonymous.milliytravel.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "trip_location")
@Getter
@Setter
@NoArgsConstructor
public class TripLocationEntity extends BaseEntity {

    @Column(name = "location_id")
    private String locationId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", insertable = false, updatable = false)
    private LocationEntity location;

    @Column(name = "trip_id")
    private String tripId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", insertable = false, updatable = false)
    private TripEntity trip;


    public TripLocationEntity(String locationId, String tripId) {
        this.locationId = locationId;
        this.tripId = tripId;
    }
}
