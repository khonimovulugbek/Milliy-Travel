package milliy.anonymous.milliytravel.service;

import milliy.anonymous.milliytravel.dto.detail.LocationDTO;
import milliy.anonymous.milliytravel.entity.LocationEntity;


public interface LocationService {

     LocationDTO create(LocationDTO dto);

     Boolean delete(String id);

     LocationDTO toDTO(LocationEntity entity);

     LocationDTO get(String locationId);
}
