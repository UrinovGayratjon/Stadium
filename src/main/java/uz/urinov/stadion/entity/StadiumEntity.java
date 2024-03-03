package uz.urinov.stadion.entity;

import jakarta.persistence.*;
import lombok.Data;
import uz.urinov.stadion.entity.base.AbstractEntity;

import java.util.List;

@Entity
@Table
@Data
public class StadiumEntity extends AbstractEntity {
    private String name;
    private String address;
    private String contact;
    private Double lat;
    private Double lon;
    private Long hourlyPrice;

    @ManyToOne
    private UserEntity owner;

    @OneToMany()
    private List<AttachmentEntity> attachmentEntities;

    @OneToMany(mappedBy = "stadiumEntity", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<OrderEntity> orderEntityList;
}
