package milliy.anonymous.milliytravel.repository;

import milliy.anonymous.milliytravel.entity.TripEntity;
import milliy.anonymous.milliytravel.mapper.TripRateMapper;
import milliy.anonymous.milliytravel.mapper.trip.TripInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TripRepository extends JpaRepository<TripEntity, String> {

    @Query("select  t from TripEntity t " +
            " inner join t.guide g " +
            " inner join g.profile pr " +
            " where t.id = ?1 " +
            " and t.priceId is not null " +
            " and t.deletedDate is null" +
            " and pr.deletedDate is null" +
            " and g.deletedDate is null ")
    Optional<TripEntity> findByIdAndDeletedDateIsNull(String id);


    @Query("select  t from TripEntity t " +
            " inner join t.guide g " +
            " inner join g.profile pr " +
            " where t.id = ?1 " +
            " and t.deletedDate is null" +
            " and pr.deletedDate is null" +
            " and g.deletedDate is null ")
    Optional<TripEntity> getTrip(String id);


//    Page<TripEntity> findByGuide_Profile_PhoneNumberAndDeletedDateIsNull(String phone, Pageable pageable);


//    @Query("select  t from TripEntity t " +
//            " inner join t.guide g " +
//            " where t.guideId = ?1 " +
//            " and t.priceId is not null " +
//            " and t.deletedDate is null" +
//            " and g.deletedDate is null ")
//    Page<TripEntity> getTripByGuideId(String guideId, Pageable pageable);


    @Query(value = "SELECT t.id as id, t.name as title," +
            "t.priceId as price_id, p.cost as price_cost, " +
            "p.currency as price_currency, p.type as price_type, " +
            "t.guideId as guide_id," +
            " avg (r.rate) as rate,count(r.id) as count " +
            " FROM  TripEntity AS t " +
            "left join ReviewEntity r on r.tripId = t.id " +
            "inner join t.price p " +
            "inner join t.guide g " +
            "inner join g.profile pr " +
            "where t.priceId is not null " +
            "and t.deletedDate is null " +
            "and g.deletedDate is null " +
            "and pr.deletedDate is null " +
            "group by t.id, t.name, t.priceId," +
            "p.cost,p.currency,p.type,t.guideId")
    Page<TripRateMapper> getRate(Pageable pageable);

    @Transactional
    @Modifying
    @Query("update TripEntity set deletedDate = ?2 where id = ?1 and deletedDate is null ")
    void updateDeletedDate(String tripId, LocalDateTime deletedDate);




    @Query(value = "select t.id as t_id, t.name as t_name, t.description as t_description, " +
            "t.minPeople as t_min_people, t.maxPeople as t_max_people, t.guideId as g_id, g.profileId as profile_id, " +
            "t.phoneNumber as t_phone_number,  t.days as t_days, " +
            " t.createdDate as t_created_date, t.updatedDate as t_updated_date, " +
            "p.id as p_id, p.cost as p_cost, p.currency as p_currency, p.type as p_type, avg (r.rate) as t_rate " +
            "from  TripEntity as t " +
            "left join ReviewEntity r on r.tripId = t.id " +
            "inner join t.price p " +
            "inner join t.guide g " +
            "inner join g.profile pr " +
            "where t.id = ?1 " +
            "and t.priceId is not null " +
            "and t.deletedDate is null " +
            "and g.deletedDate is null " +
            "and pr.deletedDate is null " +
            "group by t.id, t.name, t.description, t.minPeople, t.maxPeople, t.guideId, g.profileId, " +
            "t.days, t.phoneNumber, t.createdDate, t.updatedDate, " +
            "p.id, p.cost, p.currency, p.type ")
    Optional<TripInfoMapper> findByIdMapper(String id);

    @Query("select t.id as t_id, t.name as t_name, t.description as t_description, " +
            "t.minPeople as t_min_people, t.maxPeople as t_max_people, t.guideId as g_id, g.profileId as profile_id, " +
            "t.phoneNumber as t_phone_number, t.days as t_days, " +
            " t.createdDate as t_created_date, t.updatedDate as t_updated_date, " +
            "p.id as p_id, p.cost as p_cost, p.currency as p_currency, p.type as p_type, avg (r.rate) as t_rate " +
            "from TripEntity as t " +
            "left join ReviewEntity r on r.tripId = t.id " +
            "inner join t.guide g " +
            "inner join t.price p " +
            "inner join g.profile pr " +
            "where t.guideId = :guideId " +
            "and t.priceId is not null " +
            "and t.deletedDate is null " +
            "and g.deletedDate is null " +
            "and pr.deletedDate is null " +
            "group by t.id, t.name, t.description, t.minPeople, t.maxPeople, t.guideId, g.profileId, " +
            "t.days, t.phoneNumber,  t.createdDate, t.updatedDate, " +
            "p.id, p.cost, p.currency, p.type ")
    Page<TripInfoMapper> findTripByGuideIdMapper(@Param("guideId") String guideId, Pageable pageable);


    @Query(value = "SELECT t.id as id, t.name as title," +
            "t.priceId as price_id, p.cost as price_cost, " +
            "p.currency as price_currency, p.type as price_type, " +
            "t.guideId as guide_id " +
            " FROM  TripEntity AS t " +
            " inner join t.guide as g " +
            " inner join g.profile as pr " +
            "inner join t.price p " +
            "where t.priceId is not null " +
            "and t.deletedDate is null " +
            "and g.deletedDate is null " +
            "and pr.deletedDate is null " +
            "and g.id = ?1")
    Page<TripRateMapper> getByGuideId(String id, Pageable pageable);



    @Query(value = "SELECT t.id as id, t.name as title," +
            "t.priceId as price_id, p.cost as price_cost, " +
            "p.currency as price_currency, p.type as price_type, " +
            "t.guideId as guide_id " +
            " FROM  TripEntity AS t " +
            " inner join t.guide as g " +
            " inner join g.profile as pr " +
            "inner join t.price p " +
            "where t.priceId is not null " +
            "and t.deletedDate is null " +
            "and g.deletedDate is null " +
            "and pr.deletedDate is null " +
            "and t.id = ?1")
    Optional<TripRateMapper> getByTripId(String id);



    @Modifying
    @Transactional
    @Query("update TripEntity set  priceId = ?2 where id = ?1 and deletedDate is null ")
    void updatePriceId(String id, String priceId);


    @Query(value = "SELECT " +
            " avg (r.rate)  " +
            " FROM  TripEntity AS t " +
            "left join ReviewEntity r on r.tripId = t.id " +
            "inner join t.price p " +
            "inner join t.guide g " +
            "inner join g.profile pr " +
            "where t.priceId is not null " +
            "and t.deletedDate is null " +
            "and g.deletedDate is null " +
            "and pr.deletedDate is null " +
            " and t.id = ?1 ")
    Double getRateByTripId(String tripId);
}


