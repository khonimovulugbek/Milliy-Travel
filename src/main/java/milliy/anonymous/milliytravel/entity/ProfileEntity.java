package milliy.anonymous.milliytravel.entity;

import milliy.anonymous.milliytravel.enums.AppLang;
import milliy.anonymous.milliytravel.enums.Gender;
import milliy.anonymous.milliytravel.enums.ProfileRole;
import milliy.anonymous.milliytravel.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "profile", uniqueConstraints = @UniqueConstraint(columnNames = {"phone_number", "deleted_date"}))
@Getter
@Setter
@ToString
public class ProfileEntity extends BaseEntity {

    @Column
    private String name;

    @Column
    private String surname;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column
    private String email;

    @Column
    @Enumerated(EnumType.STRING)
    private ProfileRole role;

    @Column
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;

    @Column(name = "photo")
    private String photo;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birth_date")
    private String birthDate;

    @Column
    @Enumerated(EnumType.STRING)
    private AppLang appLanguage;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;




}
