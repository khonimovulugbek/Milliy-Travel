package milliy.anonymous.milliytravel.mapper.trip;

import milliy.anonymous.milliytravel.enums.Currency;
import milliy.anonymous.milliytravel.enums.TourType;

import java.time.LocalDateTime;

public interface TripInfoMapper {

    String getT_id();
    String getT_name();
    String getT_description();
    Integer getT_min_people();
    Integer getT_max_people();
    String getT_phone_number();
    Double getT_rate();
    Integer getT_days();
    LocalDateTime getT_created_date();
    LocalDateTime getT_updated_date();

    String getP_id();
    Long getP_cost();
    Currency getP_currency();
    TourType getP_type();

    String getG_id();
    String getProfile_id();
}
