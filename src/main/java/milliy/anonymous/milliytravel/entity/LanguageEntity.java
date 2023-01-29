package milliy.anonymous.milliytravel.entity;

import milliy.anonymous.milliytravel.enums.LanguageLevel;
import milliy.anonymous.milliytravel.enums.LanguageName;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "language")
public class LanguageEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private LanguageName name;


    @Enumerated(EnumType.STRING)
    private LanguageLevel level;
}
