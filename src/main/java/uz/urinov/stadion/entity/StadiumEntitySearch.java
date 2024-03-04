package uz.urinov.stadion.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import uz.urinov.stadion.entity.base.AbstractEntity;

import java.sql.Timestamp;
import java.util.List;


@Data
public class StadiumEntitySearch extends AbstractEntity {

    private Long id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String address;
    private String contact;
    private Long hourlyPrice;
    private Double lat;
    private Double lon;
    private String name;
    private Long ownerId;
}
