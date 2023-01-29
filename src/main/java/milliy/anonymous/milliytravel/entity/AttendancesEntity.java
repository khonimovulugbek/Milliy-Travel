package milliy.anonymous.milliytravel.entity;

import milliy.anonymous.milliytravel.enums.AttendancesStatus;
import milliy.anonymous.milliytravel.enums.AttendancesType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "attendances")
public class AttendancesEntity extends BaseEntity {


    @Enumerated(EnumType.STRING)
    private AttendancesType type;

    @Column(name = "profile_id", nullable = false)
    private String profileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;


    @Column(name = "guide_id")
    private String guideId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id", insertable = false, updatable = false)
    private GuideEntity guide;


    @Column(name = "trip_id")
    private String tripId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", insertable = false, updatable = false)
    private TripEntity trip;


    @Enumerated(EnumType.STRING)
    private AttendancesStatus status;
}
