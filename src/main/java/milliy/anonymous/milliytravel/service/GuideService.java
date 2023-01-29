package milliy.anonymous.milliytravel.service;

import milliy.anonymous.milliytravel.dto.Result;
import milliy.anonymous.milliytravel.dto.guide.GuideDTO;
import milliy.anonymous.milliytravel.dto.guide.GuideResDTO;
import milliy.anonymous.milliytravel.dto.guide.GuideSearchDTO;
import milliy.anonymous.milliytravel.dto.guide.GuideShortInfoDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;

import java.util.List;


public interface GuideService {


     GuideDTO create(GuideDTO dto);

     ActionDTO update(GuideDTO dto);

     Boolean getGuideHiring();

/*
     GuideDTO toDTO(GuideRateMapper mapper);

     GuideDTO toDTO(GuideFilterMapper mapper);

     GuideDTO toDTO(GuideEntity entity);
*/

     Boolean updateIsHiring();

     ActionDTO delete();

     GuideResDTO filter(GuideSearchDTO dto, int page);

}
