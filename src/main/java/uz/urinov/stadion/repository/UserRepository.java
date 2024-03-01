package uz.urinov.stadion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.urinov.stadion.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String email);


}
