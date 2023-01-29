package milliy.anonymous.milliytravel.mapper;

import milliy.anonymous.milliytravel.enums.Currency;
import milliy.anonymous.milliytravel.enums.TourType;

public interface GuideRateMapper {

     String getGuideId();

     String getProfileId();

     String getPriceId();

     Long getPrice_cost();

     Currency getPrice_currency();

     TourType getPrice_type();

     String getPhoto();

     String getName();

     String getSurname();
     Double getRate();

     String getPhone();



}
