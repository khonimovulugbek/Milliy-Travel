package milliy.anonymous.milliytravel.repository;

import milliy.anonymous.milliytravel.entity.AttendancesEntity;
import milliy.anonymous.milliytravel.enums.AttendancesStatus;
import milliy.anonymous.milliytravel.enums.AttendancesType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendancesRepository extends JpaRepository<AttendancesEntity, String> {

    List<AttendancesEntity> findByGuideIdAndTypeAndStatus(String guideId, AttendancesType type, AttendancesStatus status);

    List<AttendancesEntity> findByTripIdAndTypeAndStatus(String tripId, AttendancesType type, AttendancesStatus status);


    List<AttendancesEntity> findByStatusAndCreatedDateIsAfter(AttendancesStatus status, LocalDateTime time);

    @Modifying
    @Transactional
    @Query("update  AttendancesEntity set  status = ?1,updatedDate = ?2 where  id in (?3)")
    void updateStatus(AttendancesStatus status, LocalDateTime time,List<String> idList);
}