package milliy.anonymous.milliytravel.repository;

import milliy.anonymous.milliytravel.entity.AttachEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttachRepository extends JpaRepository<AttachEntity, String> {

    Optional<AttachEntity> findByWebContentLink(String link);


}