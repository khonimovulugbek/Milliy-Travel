package milliy.anonymous.milliytravel.repository;

import milliy.anonymous.milliytravel.entity.GuideEntity;
import milliy.anonymous.milliytravel.enums.ProfileRole;
import milliy.anonymous.milliytravel.mapper.GuideRateMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GuideRepository extends JpaRepository<GuideEntity, String> {

    Optional<GuideEntity> findByProfileIdAndDeletedDateIsNull(String pId);

    Optional<GuideEntity> findByProfile_PhoneNumberAndProfile_DeletedDateIsNull(String phone);

    Optional<GuideEntity> findByProfile_PhoneNumberAndProfile_DeletedDateIsNullAndDeletedDateIsNull(String phone);

    @Modifying
    @Transactional
    @Query("update GuideEntity as g set g.isHiring =:isHiring where g.profileId = :profileId")
    void updateIsHiring(@Param("profileId") String profileId, @Param("isHiring") Boolean isHiring);


    @Query(value = "select g.id as guideId,g.profileId as profileId," +
            " g.priceId as priceId," +
            " p.cost as price_cost, p.currency as price_currency, p.type as price_type," +
            " pr.photo as photo,pr.name as name,pr.surname as surname, avg (r.rate)  as rate " +
            " from GuideEntity as g " +
            " left outer join ReviewEntity as r on r.guideId = g.id " +
            " inner join g.price as p " +
            " inner join g.profile as pr " +
            " where pr.role = :role and " +
            " g.deletedDate is null and " +
            " pr.deletedDate is null and " +
            "  g.isHiring = true " +
            " group by g.id,g.profileId ," +
            " g.priceId,p.cost ,p.currency,p.type," +
            " pr.photo,pr.name ,pr.surname ")
    List<GuideRateMapper> getRate(Pageable pageable, @Param("role") ProfileRole role);


    @Modifying
    @Transactional
    @Query("update GuideEntity  set  deletedDate = ?2 where id = ?1 and  deletedDate is null ")
    void updateDeleteDate(String id, LocalDateTime now);


    Optional<GuideEntity> findByIdAndDeletedDateIsNull(String guideId);


    @Query("select g.id as guideId, g.profileId as profileId," +
            " g.priceId as priceId, pr.cost as price_cost," +
            " pr.currency as price_currency, pr.type as price_type," +
            " p.photo as photo, p.name as name,p.surname as surname,p.phoneNumber as phone, " +
            " avg(r.rate) as rate from GuideEntity g  " +
            " left outer join ReviewEntity as r on r.guideId = g.id " +
            " inner join  g.price pr " +
            " inner join g.profile p " +
            " where g.deletedDate is null " +
            " and p.deletedDate is null and  g.id = ?1" +
            " group by g.id, g.profileId ," +
            " g.priceId , pr.cost ," +
            " pr.currency , pr.type ," +
            " p.photo , p.name,p.surname ,p.phoneNumber")
    Optional<GuideRateMapper> getShortInfo(String guideId);


    @Query(value = "select  avg (r.rate)   " +
            " from GuideEntity as g " +
            " left outer join ReviewEntity as r on r.guideId = g.id " +
            " inner join g.price as p " +
            " inner join g.profile as pr " +
            " where pr.role = ?2 and " +
            " g.deletedDate is null and " +
            " pr.deletedDate is null " +
            " and g.isHiring = true and g.id = ?1")
    Double getRateByGuideId(String guideId, ProfileRole role);
}