package com.zjgsu.kirateresa.BiograFi_Backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 地名坐标缓存模型
 */
@Entity
@Table(name = "location_geocodes", uniqueConstraints = {
        @UniqueConstraint(name = "unique_name", columnNames = {"name"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class LocationGeocode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "lng", precision = 10, scale = 6)
    private BigDecimal lng;

    @Column(name = "lat", precision = 10, scale = 6)
    private BigDecimal lat;

    @Column(name = "matched_name", length = 255)
    private String matchedName;

    @Enumerated(EnumType.STRING)
    @Column(name = "confidence", length = 10)
    private ConfidenceLevel confidence;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 置信度枚举
     */
    public enum ConfidenceLevel {
        high, medium, low
    }

}
