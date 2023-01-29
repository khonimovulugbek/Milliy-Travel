package milliy.anonymous.milliytravel.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "guide_location")
public class GuideLocationEntity extends BaseEntity {


    @Column(name = "location_id")
    private String locationId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id",insertable = false,updatable = false)
    private LocationEntity location;


    @Column(name = "guide_id")
    private String guideId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id",insertable = false,updatable = false)
    private GuideEntity guide;
}
