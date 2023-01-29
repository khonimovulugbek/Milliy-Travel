package milliy.anonymous.milliytravel.repository;

import milliy.anonymous.milliytravel.entity.TripLocationEntity;
import milliy.anonymous.milliytravel.mapper.trip.TripLocationMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TripLocationRepository extends JpaRepository<TripLocationEntity, String> {

    @Modifying
    @Transactional
    @Query("update TripLocationEntity set tripId = ?2 where id in (?1)")
    void updateTripId(List<String> idList, String tripId);

    @Query("select tl.tripId as tripId,tl.locationId as locationId," +
            " l.description as description,l.district as district, " +
            " l.provence as provence " +
            "  from TripLocationEntity tl " +
            " inner join tl.location l " +
            "where tl.tripId =?1")
    List<TripLocationMapper> getLocationByTripId(String tripId);
}