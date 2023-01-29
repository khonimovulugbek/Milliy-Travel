package milliy.anonymous.milliytravel.service;

import milliy.anonymous.milliytravel.dto.detail.LanguageDTO;
import milliy.anonymous.milliytravel.dto.guide.GuideLanguageDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.entity.GuideLanguageEntity;

import java.util.List;

public interface GuideLanguageService {


    ActionDTO create(GuideLanguageDTO dto);

    List<LanguageDTO> getLanguageByGuideId(String guideId);

    List<GuideLanguageEntity> findLanguageByGuideId(String guideId);

    ActionDTO deleteByGuideId(String guideId);

}
