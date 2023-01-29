package milliy.anonymous.milliytravel.entity;

import milliy.anonymous.milliytravel.enums.AppLang;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "profile_detail")
@Getter
@Setter
public class ProfileDetailEntity extends BaseEntity {

    public static final int TTL = 3; // minutes

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "sms_code")
    private String smsCode;

    @Column(name = "app_language")
    @Enumerated(EnumType.STRING)
    private AppLang appLanguage;


}
