package milliy.anonymous.milliytravel.mapper;


import milliy.anonymous.milliytravel.enums.Currency;
import milliy.anonymous.milliytravel.enums.TourType;

public interface TripRateMapper {

    String getId();

    String getTitle();

    String getPrice_id();

    Long getPrice_cost();

    Currency getPrice_currency();

    TourType getPrice_type();

    String getGuide_id();

    Double getRate();
}
