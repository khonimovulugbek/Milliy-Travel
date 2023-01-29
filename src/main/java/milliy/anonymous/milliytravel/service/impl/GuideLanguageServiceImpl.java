package milliy.anonymous.milliytravel.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.dto.detail.LanguageDTO;
import milliy.anonymous.milliytravel.dto.guide.GuideLanguageDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.entity.BaseEntity;
import milliy.anonymous.milliytravel.entity.GuideEntity;
import milliy.anonymous.milliytravel.entity.GuideLanguageEntity;
import milliy.anonymous.milliytravel.mapper.GuideLanguageMapper;
import milliy.anonymous.milliytravel.repository.GuideLanguageRepository;
import milliy.anonymous.milliytravel.repository.GuideRepository;
import milliy.anonymous.milliytravel.service.GuideLanguageService;
import milliy.anonymous.milliytravel.service.LanguageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuideLanguageServiceImpl implements GuideLanguageService {

    private final GuideRepository guideService;
    private final LanguageService languageService;
    private final GuideLanguageRepository guideLanguageRepository;



    public ActionDTO create(GuideLanguageDTO dto) {

        GuideEntity guideEntity = guideService.findByIdAndDeletedDateIsNull(dto.getGuideId()).orElse(null);

        if (Optional.ofNullable(guideEntity).isEmpty()) {
            log.warn("guide not found!  GuideLanguageServiceImpl ={}", dto.getGuideId());
            return new ActionDTO(false);
        }

        if (Optional.ofNullable(languageService.get(dto.getLanguageId())).isEmpty()) {
            log.warn("Language not found! GuideLanguageServiceImpl ={}", dto.getLanguageId());
            return new ActionDTO(false);
        }

        GuideLanguageEntity entity = new GuideLanguageEntity();
        entity.setGuideId(dto.getGuideId());
        entity.setLanguageId(dto.getLanguageId());

        guideLanguageRepository.save(entity);

        return new ActionDTO(true);
    }

    public List<LanguageDTO> getLanguageByGuideId(String guideId) {

        GuideEntity guideEntity = guideService.findByIdAndDeletedDateIsNull(guideId).orElse(null);

        if (Optional.ofNullable(guideEntity).isEmpty()) {
            log.warn("guide not found!  GuideLanguageServiceImpl ={}", guideId);
            return null;
        }

      return guideLanguageRepository.getByGuideId(guideId).stream().map(this::toDTO).toList();
    }

    public List<GuideLanguageEntity> findLanguageByGuideId(String guideId) {
        List<GuideLanguageEntity> entityList = guideLanguageRepository.findByGuideId(guideId);
        guideLanguageRepository.deleteByList(entityList.stream().map(BaseEntity::getId).toList());
        return entityList;
    }


    public ActionDTO deleteByGuideId(String guideId) {

        GuideEntity guideEntity = guideService.findByIdAndDeletedDateIsNull(guideId).orElse(null);

        if (Optional.ofNullable(guideEntity).isEmpty()) {
            log.warn("guide not found!  GuideLanguageServiceImpl ={}", guideId);
            return new ActionDTO(false);
        }

        guideLanguageRepository.deleteByGuideId(guideId);

        return new ActionDTO(true);
    }

    public LanguageDTO toDTO(GuideLanguageMapper mapper) {

        LanguageDTO dto = new LanguageDTO();
        dto.setId(mapper.getLId());
        dto.setLevel(mapper.getLevel());
        dto.setName(mapper.getName());

        return dto;
    }
}
