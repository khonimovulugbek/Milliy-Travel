package milliy.anonymous.milliytravel.mapper;

import milliy.anonymous.milliytravel.enums.LanguageLevel;
import milliy.anonymous.milliytravel.enums.LanguageName;



public interface GuideLanguageMapper {

    String getId();

    String getGuideId();

    String getLId();

    LanguageName getName();

    LanguageLevel getLevel();
}
