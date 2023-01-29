package milliy.anonymous.milliytravel.entity;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "facility")
@Getter
@Setter
public class FacilityEntity extends BaseEntity {

    private String title;

    private String description;

}

