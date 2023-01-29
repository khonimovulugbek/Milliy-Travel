package milliy.anonymous.milliytravel.repository;

import milliy.anonymous.milliytravel.entity.DistrictEntity;
import milliy.anonymous.milliytravel.enums.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<DistrictEntity,Integer> {

    List<DistrictEntity> findAllByRegion(Region region);

}