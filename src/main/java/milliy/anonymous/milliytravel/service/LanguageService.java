package milliy.anonymous.milliytravel.service;

import milliy.anonymous.milliytravel.dto.detail.LanguageDTO;
import milliy.anonymous.milliytravel.entity.LanguageEntity;


public interface LanguageService {

     LanguageEntity create(LanguageDTO dto);

     LanguageDTO get(String id);

     Boolean delete(String id);

     LanguageDTO toDTO(LanguageEntity entity);

}
