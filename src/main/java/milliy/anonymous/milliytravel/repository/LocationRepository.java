package milliy.anonymous.milliytravel.repository;

import milliy.anonymous.milliytravel.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationEntity, String> {
}