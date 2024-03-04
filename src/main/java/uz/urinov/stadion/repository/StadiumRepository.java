package uz.urinov.stadion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import uz.urinov.stadion.entity.StadiumEntity;
import uz.urinov.stadion.entity.StadiumEntitySearch;

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

    @Query("SELECT se FROM StadiumEntity se " +
            "WHERE se.id NOT IN (SELECT s.id FROM StadiumEntity s INNER JOIN s.orderEntityList o WHERE (o.date=:date and not (:end<=o.startTime or :start>=o.endTime)))  order by (((se.lat - :lat) * (se.lat - :lat)) + ((se.lon - :lon) * (se.lon - :lon)))")
    List<StadiumEntity> getAllBySortingByFreeTime2(
            @Param("date") LocalDate date,
            @Param("start") LocalTime start,
            @Param("end") LocalTime end,
            @Param("lat") Double lat,
            @Param("lon") Double lon
    );

    @Query(value = "Select * from stadium_entity s where (" +
            " s.id not in(" +
            "Select se.id from stadium_entity se inner join order_stadium os " +
            "on se.id=os.stadium_entity_id where (os.date=:date and not(:end<=os.start_time or :start>=os.end_time))" +
            ")) order by (((s.lat - :lat) * (s.lat - :lat)) + ((s.lon - :lon ) * (s.lon - :lon)))",
            nativeQuery = true)
    List<StadiumEntitySearch> getAllBySortingByFreeTime5(
            @Param("date") LocalDate date,
            @Param("start") LocalTime start,
            @Param("end") LocalTime end,
            @Param("lat") Double lat,
            @Param("lon") Double lon
    );

    int deleteByIdAndOwnerId(Long stadiumId, Long ownerId);

}
