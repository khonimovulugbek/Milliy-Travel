package milliy.anonymous.milliytravel.repository;

import milliy.anonymous.milliytravel.entity.GuideLanguageEntity;
import milliy.anonymous.milliytravel.mapper.GuideLanguageMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GuideLanguageRepository extends JpaRepository<GuideLanguageEntity, String> {


    @Query("select gl.id as id,gl.guideId as guideId, l.name as name," +
            "l.level as level,l.id as lId " +
            "from GuideLanguageEntity gl " +
            " inner join gl.language as l " +
            " where  gl.guideId = ?1")
    List<GuideLanguageMapper> getByGuideId(String guideId);

    @Query("select gl from GuideLanguageEntity gl " +
            " inner join gl.language as l " +
            " where  gl.guideId = ?1")
    List<GuideLanguageEntity> findByGuideId(String guideId);


    @Modifying
    @Transactional
    @Query("delete from GuideLanguageEntity  " +
            " where id in (?1) ")
    void deleteByList(List<String> idList);

    void deleteByGuideId(String guideId);

}