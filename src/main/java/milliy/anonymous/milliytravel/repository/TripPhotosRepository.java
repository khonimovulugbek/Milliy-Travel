package milliy.anonymous.milliytravel.repository;

import milliy.anonymous.milliytravel.entity.TripPhotoEntity;
import milliy.anonymous.milliytravel.mapper.trip.TripLocationMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TripPhotosRepository extends JpaRepository<TripPhotoEntity, String> {

    List<TripPhotoEntity> findAllByTripId(String tripId);


    Optional<TripPhotoEntity> findTopByTripIdOrderByCreatedDateAsc(String tripId);

    @Transactional
    @Modifying
    @Query("update TripPhotoEntity set  tripId = ?2 where id in (?1)")
    void updateTripId(List<String> photos, String tripId);




    @Query("select tl.tripId as tripId,tl.locationId as locationId," +
            " l.description as description,l.district as district, " +
            " l.provence as provence " +
            "  from TripPhotoEntity tl " +
            " inner join tl.location l " +
            "where tl.tripId =?1")
    List<TripLocationMapper> getLocationByTripId(String tripId);
}