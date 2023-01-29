package milliy.anonymous.milliytravel.repository;

import milliy.anonymous.milliytravel.entity.GuideLocationEntity;
import milliy.anonymous.milliytravel.mapper.LocationMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GuideLocationRepository extends JpaRepository<GuideLocationEntity, String> {

    @Query("select l.provence as provence,l.district as district," +
            " l.description as description  from GuideLocationEntity gl " +
            "inner join gl.guide g" +
            " inner join gl.location l" +
            " where  g.id = ?1")
    List<LocationMapper> getLocationByGuideId(String guideId);

    @Query("select gl from GuideLocationEntity gl " +
            "inner join gl.guide g" +
            " inner join gl.location l" +
            " where  g.id = ?1")
    List<GuideLocationEntity> findLocationByGuideId(String guideId);

    @Modifying
    @Transactional
    @Query("delete from GuideLocationEntity  " +
            " where id in (?1) ")
    void deleteByList(List<String> idList);

}
