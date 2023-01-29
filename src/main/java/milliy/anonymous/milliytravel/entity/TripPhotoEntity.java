package milliy.anonymous.milliytravel.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "trip_photo")
public class TripPhotoEntity extends BaseEntity {

    @Column(name = "attach_id")
    private String photo;


    @Column(name = "trip_id")
    private String tripId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id",insertable = false,updatable = false)
    private TripEntity tripEntity;



    @Column(name = "location_id")
    private String locationId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id",insertable = false,updatable = false)
    private LocationEntity location;




}
