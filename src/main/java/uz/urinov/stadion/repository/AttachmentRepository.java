package uz.urinov.stadion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.urinov.stadion.entity.AttachmentEntity;


public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Long> {
}
