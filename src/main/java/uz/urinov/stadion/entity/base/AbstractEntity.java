package uz.urinov.stadion.entity.base;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@MappedSuperclass
@Data
public class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updatedAt;

//    @JoinColumn(updatable = false)
//    @CreatedBy
//    @ManyToOne(fetch = FetchType.LAZY)
//    private User createdBy;
//
//    @JoinColumn()
//    @LastModifiedBy
//    @ManyToOne(fetch = FetchType.LAZY)
//    private User updatedBy;


}
