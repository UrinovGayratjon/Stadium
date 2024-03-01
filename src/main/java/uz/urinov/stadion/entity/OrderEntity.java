package uz.urinov.stadion.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import uz.urinov.stadion.entity.base.AbstractEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "order_stadium")
@Getter
@Setter
public class OrderEntity extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity orderOwner;

    @ManyToOne(fetch = FetchType.LAZY)
    private StadiumEntity stadiumEntity;

    @Column(name = "date")
    private LocalDate date;
    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "end_time")
    private LocalTime endTime;

}
