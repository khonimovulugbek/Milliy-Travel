package milliy.anonymous.milliytravel.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.dto.detail.GuideLocationDTO;
import milliy.anonymous.milliytravel.dto.detail.LocationDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.entity.BaseEntity;
import milliy.anonymous.milliytravel.entity.GuideLocationEntity;
import milliy.anonymous.milliytravel.mapper.LocationMapper;
import milliy.anonymous.milliytravel.repository.GuideLocationRepository;
import milliy.anonymous.milliytravel.service.GuideLocationService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class GuideLocationServiceImpl implements GuideLocationService {

    private final GuideLocationRepository guideLocationRepository;

    public ActionDTO create(GuideLocationDTO dto) {

        GuideLocationEntity entity = new GuideLocationEntity();

        entity.setGuideId(dto.getGuideId());
        entity.setLocationId(dto.getLocationId());

        guideLocationRepository.save(entity);

        return new ActionDTO(true);
    }

    public ActionDTO delete() {
        return null;
    }

    public List<LocationDTO> getLocationByGuideId(String guideId){
        return guideLocationRepository.getLocationByGuideId(guideId).stream().map(this::toLocationDTO).toList();
    }

    public List<GuideLocationEntity> findLocationByGuideId(String guideId){
        List<GuideLocationEntity> entityList = guideLocationRepository.findLocationByGuideId(guideId);
        guideLocationRepository.deleteByList(entityList.stream().map(BaseEntity::getId).toList());
        return entityList;
    }

    public LocationDTO toLocationDTO(LocationMapper mapper){
        LocationDTO dto = new LocationDTO();
        dto.setDescription(mapper.getDescription());
        dto.setDistrict(mapper.getDistrict());
        dto.setProvence(mapper.getProvence());
        return dto;
    }


}
