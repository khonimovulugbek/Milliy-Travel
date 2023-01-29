package milliy.anonymous.milliytravel.entity;

import milliy.anonymous.milliytravel.enums.DeviceType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

@Entity
@Table(name = "device", uniqueConstraints = @UniqueConstraint(columnNames = {"device_id", "device_token", "profile_detail_id"}))
@Getter
@Setter
public class DeviceEntity extends BaseEntity {

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "device_token")
    private String deviceToken;

    @Column(name = "device_type")
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    @Column(name = "profile_detail_id", nullable = false)
    private String profileDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "profile_detail_id", insertable = false, updatable = false)
    private ProfileDetailEntity profileDetail;

}
