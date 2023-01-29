package milliy.anonymous.milliytravel.service;

import milliy.anonymous.milliytravel.dto.request.FacilityDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.entity.FacilityEntity;


public interface FacilityService {
     ActionDTO create(FacilityDTO dto);
     FacilityDTO get(String id);
     FacilityDTO toDTO(FacilityEntity entity);
}
