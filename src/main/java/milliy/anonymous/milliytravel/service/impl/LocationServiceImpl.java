package milliy.anonymous.milliytravel.service.impl;

import milliy.anonymous.milliytravel.dto.detail.LocationDTO;
import milliy.anonymous.milliytravel.entity.LocationEntity;
import milliy.anonymous.milliytravel.exception.ItemNotFoundException;
import milliy.anonymous.milliytravel.repository.LocationRepository;
import milliy.anonymous.milliytravel.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationDTO create(LocationDTO dto) {
        LocationEntity entity = new LocationEntity();

        entity.setDescription(dto.getDescription());
        entity.setDistrict(dto.getDistrict());
        entity.setProvence(dto.getProvence());

        locationRepository.save(entity);

        return toDTO(entity);
    }

    public Boolean delete(String id) {
        locationRepository.findById(id).orElseThrow(() -> {
            log.warn("Location Not Found! {}", id);
            throw new ItemNotFoundException("Location not found!");
        });

        locationRepository.deleteById(id);
        return true;
    }

    public LocationDTO toDTO(LocationEntity entity) {
        LocationDTO dto = new LocationDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setProvence(entity.getProvence());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setDistrict(entity.getDistrict());
        return dto;
    }

    public LocationDTO get(String locationId) {
        LocationEntity entity = locationRepository
                .findById(locationId)
                .orElse(null);

        if (Optional.ofNullable(entity).isEmpty()) return null;

        return toDTO(entity);
    }
}
