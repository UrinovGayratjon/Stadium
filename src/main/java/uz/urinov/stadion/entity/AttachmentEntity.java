package uz.urinov.stadion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.urinov.stadion.entity.base.AbstractEntity;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttachmentEntity extends AbstractEntity {

    private String fileOriginalName;

    private Long size;

    private String contentType;

    private String name;
}
