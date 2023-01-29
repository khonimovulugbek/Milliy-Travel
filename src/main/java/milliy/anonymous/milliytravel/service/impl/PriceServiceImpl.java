package milliy.anonymous.milliytravel.service.impl;


import milliy.anonymous.milliytravel.dto.detail.PriceDTO;
import milliy.anonymous.milliytravel.entity.PriceEntity;
import milliy.anonymous.milliytravel.repository.PriceRepository;
import milliy.anonymous.milliytravel.service.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    public PriceDTO create(PriceDTO dto) {
        PriceEntity entity = new PriceEntity();
        entity.setCost(dto.getCost());
        entity.setCurrency(dto.getCurrency());
        entity.setType(dto.getType());
        priceRepository.save(entity);

        return toDTO(entity);
    }

    public PriceDTO get(String id){
        PriceEntity priceEntity = priceRepository.findById(id).orElse(null);

        if(priceEntity == null){
            return null;
        }
        return toDTO(priceEntity);
    }

    public PriceDTO toDTO(PriceEntity entity) {
        PriceDTO dto = new PriceDTO();
        dto.setId(entity.getId());
        dto.setCost(entity.getCost());
        dto.setCurrency(entity.getCurrency());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setType(entity.getType());
        return dto;
    }
}
