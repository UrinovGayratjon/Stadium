package uz.urinov.stadion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import uz.urinov.stadion.entity.StadiumEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface StadiumRepository extends JpaRepository<StadiumEntity, Long> {

    List<StadiumEntity> getAllBy();

    @Query("SELECT s FROM StadiumEntity s order by (((s.lat - :lat) * (s.lat - :lat)) + ((s.lon - :lon) * (s.lon - :lon)))")
    List<StadiumEntity> getAllBySorting(
            @Param("lat") Double lat,
            @Param("lon") Double lon
    );

    @Query("SELECT s FROM StadiumEntity s order by (((s.lat - :lat) * (s.lat - :lat)) + ((s.lon - :lon) * (s.lon - :lon)))")
    List<StadiumEntity> getAllBySortingByFreeTime(
            @Param("lat") Double lat,
            @Param("lon") Double lon
    );

    @Query("SELECT s From StadiumEntity s inner join s.orderEntityList o where (o.date!=:date or (:end<=o.startTime and :start>=o.endTime)) order by (((s.lat - :lat) * (s.lat - :lat)) + ((s.lon - :lon) * (s.lon - :lon)))")
    List<StadiumEntity> getAllBySortingByFreeTime2(
            @Param("date") LocalDate date,
            @Param("start") LocalTime start,
            @Param("end") LocalTime end,
            @Param("lat") Double lat,
            @Param("lon") Double lon
    );


     boolean deleteByIdAndOwnerId(Long stadiumId, Long ownerId);



}
