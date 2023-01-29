package milliy.anonymous.milliytravel.service;


import milliy.anonymous.milliytravel.dto.detail.PriceDTO;
import milliy.anonymous.milliytravel.entity.PriceEntity;


public interface PriceService {

     PriceDTO create(PriceDTO dto);

     PriceDTO get(String id);

     PriceDTO toDTO(PriceEntity entity);
}
