package milliy.anonymous.milliytravel.repository;

import milliy.anonymous.milliytravel.entity.TripFacilityEntity;
import milliy.anonymous.milliytravel.mapper.TripFacilityMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TripFacilityRepository extends JpaRepository<TripFacilityEntity, String> {


    @Query("select tf.tripId as t_id, tf.facilityId as f_id," +
            " f.description as f_description ,f.title as f_title " +
            " from TripFacilityEntity tf " +
            " inner join tf.facility f " +
            " where  tf.tripId = ?1")
    List<TripFacilityMapper> getFacilityByTripId(String tripId);
}