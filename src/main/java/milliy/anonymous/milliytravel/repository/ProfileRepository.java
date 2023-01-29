package milliy.anonymous.milliytravel.repository;

import milliy.anonymous.milliytravel.entity.ProfileEntity;
import milliy.anonymous.milliytravel.enums.AppLang;
import milliy.anonymous.milliytravel.enums.ProfileRole;
import milliy.anonymous.milliytravel.enums.ProfileStatus;
import milliy.anonymous.milliytravel.mapper.GuideProfileInfoMapper;
import milliy.anonymous.milliytravel.mapper.ProfileGuideInfoMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, String> {

    Optional<ProfileEntity> findByPhoneNumberAndDeletedDateIsNull(String phoneNumber);

    @Query(value = "select g.id as g_id, p.id as p_id  " +
            "from guide g " +
            "  right join profile p on p.id = g.profile_id " +
            "where p.id = :id " +
            "and p.deleted_date is null ",nativeQuery = true)
    Optional<ProfileGuideInfoMapper> findGuideByProfileId(@Param("id") String id);

    @Query("select g.id as g_id," +
            "p.id as p_id, p.name as p_name, p.surname as p_surname, p.phoneNumber as p_phone_number, p.email as p_email, p.role as p_role," +
            "p.status as p_status, p.photo as p_photo, p.gender as p_gender, p.birthDate as p_birth_date, p.createdDate as p_created_date," +
            "p.updatedDate as p_updated_date,p.appLanguage as p_app_language " +
            "from ProfileEntity p " +
            "left join GuideEntity g on g.profileId = p.id " +
            "where p.phoneNumber = :phoneNumber " +
            "and p.deletedDate is null " +
            "and g.deletedDate is null ")
    Optional<GuideProfileInfoMapper> findByPhoneNumberMapper(@Param("phoneNumber") String phoneNumber);

    @Modifying
    @Transactional
    @Query("update ProfileEntity set photo = :openUrl where id = :id and deletedDate is null")
    void updateImage(@Param("openUrl") String openUrl, @Param("id") String id);

    @Modifying
    @Transactional
    @Query("update ProfileEntity set status = :status where id = :id and deletedDate is null")
    void updateStatus(@Param("status") ProfileStatus status, @Param("id") String id);


    @Transactional
    @Modifying
    @Query("update ProfileEntity p set p.role = ?1 where p.id = ?2 and p.deletedDate is null")
    void updateRole(ProfileRole role, String id);

    @Transactional
    @Modifying
    @Query("update ProfileEntity p set p.appLanguage = ?1 where p.phoneNumber = ?2 and p.deletedDate is null")
    void updateAppLanguage(AppLang appLanguage, String phoneNumber);

    @Modifying
    @Transactional
    @Query("update ProfileEntity  set  deletedDate = ?2 where id =?1 and  deletedDate is null ")
    void updateDeleteDate(String id, LocalDateTime now);
}