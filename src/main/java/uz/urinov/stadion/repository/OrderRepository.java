package uz.urinov.stadion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.urinov.stadion.entity.OrderEntity;
import uz.urinov.stadion.entity.StadiumEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query("select o from OrderEntity o where ((o.date=:date and o.stadiumEntity=:stadiumEntity)and " +
            "((:start>=o.startTime and :start<o.endTime) or (:end>o.startTime and :end<=o.endTime) or (:start<o.startTime and :end >o.endTime ) ))")
    List<OrderEntity> findByStadiumEntityAndDate(
            @Param("stadiumEntity") StadiumEntity stadiumEntity,
            @Param("date") LocalDate date,
            @Param("start") LocalTime start,
            @Param("end") LocalTime end
    );

    List<OrderEntity> findByOrderOwnerId(Long ownerId);

    boolean deleteByIdAndOrderOwnerId(Long orderId, Long ownerId);

    Optional<OrderEntity> findByIdAndOrderOwnerId(Long orderId, Long ownerId);


}
