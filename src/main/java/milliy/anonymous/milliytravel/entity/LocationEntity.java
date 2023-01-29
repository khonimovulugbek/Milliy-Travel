package milliy.anonymous.milliytravel.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "location")
public class LocationEntity extends BaseEntity {

    private String provence;

    private String district;

    private String description;

}
