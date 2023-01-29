package milliy.anonymous.milliytravel.service.impl;

import milliy.anonymous.milliytravel.dto.request.FacilityDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.entity.FacilityEntity;
import milliy.anonymous.milliytravel.repository.FacilityRepository;
import milliy.anonymous.milliytravel.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository facilityRepository;


    public ActionDTO create(FacilityDTO dto){

        FacilityEntity entity = new FacilityEntity();
        entity.setDescription(dto.getDescription());
        entity.setTitle(dto.getTitle());

        facilityRepository.save(entity);

        return new ActionDTO(true);
    }

    public FacilityDTO get(String id){

        Optional<FacilityEntity> entity = facilityRepository.findById(id);

        return entity.map(this::toDTO).orElse(null);

    }

    public FacilityDTO toDTO(FacilityEntity entity) {

        FacilityDTO dto = new FacilityDTO();
        dto.setDescription(entity.getDescription());
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());

        return dto;
    }

}
