package milliy.anonymous.milliytravel.entity;

import milliy.anonymous.milliytravel.enums.Region;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "district")
@NoArgsConstructor
public class DistrictEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private Region region;

    public DistrictEntity(String name, Region region) {
        this.name = name;
        this.region = region;
    }
}
