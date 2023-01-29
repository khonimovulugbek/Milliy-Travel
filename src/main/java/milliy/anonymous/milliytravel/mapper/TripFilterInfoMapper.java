package milliy.anonymous.milliytravel.mapper;

import milliy.anonymous.milliytravel.enums.Currency;
import milliy.anonymous.milliytravel.enums.TourType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class TripFilterInfoMapper {

    private String t_id;
    private String t_name;
    private String t_desc;
    private Integer t_max;
    private Integer t_min;
    private Integer t_days;
    private String t_phone;
    private Double t_rate;

    private List<String> facilities;
    private List<String> photos;
    private List<String> locations;

    private String p_id;
    private Long p_cost;
    private Currency p_currency;
    private TourType p_type;

    private String g_id;
    private String g_phone;
    private String g_bio;
    private Boolean g_ishiring;
    private Double g_rate;

    private String gp_id;
    private String gp_name;
    private String gp_surname;
    private String gp_photo;


}
