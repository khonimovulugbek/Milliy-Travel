package milliy.anonymous.milliytravel.service;

import milliy.anonymous.milliytravel.dto.detail.GuideLocationDTO;
import milliy.anonymous.milliytravel.dto.detail.LocationDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.entity.GuideLocationEntity;

import java.util.List;

public interface GuideLocationService {

    ActionDTO create(GuideLocationDTO dto);

    ActionDTO delete();

    List<LocationDTO> getLocationByGuideId(String guideId);

    List<GuideLocationEntity> findLocationByGuideId(String guideId);

}
