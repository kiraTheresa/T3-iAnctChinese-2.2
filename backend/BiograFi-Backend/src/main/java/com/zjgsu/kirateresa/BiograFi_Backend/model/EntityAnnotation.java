package com.zjgsu.kirateresa.BiograFi_Backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 实体标注模型
 */
@Entity
@Table(name = "entity_annotations", indexes = {
        @Index(name = "idx_document_label", columnList = "document_id, label, start_index")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class EntityAnnotation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "document_id", nullable = false, length = 64)
    private String documentId;

    @Column(name = "start_index", nullable = false)
    private Integer startIndex;

    @Column(name = "end_index", nullable = false)
    private Integer endIndex;

    @Column(name = "label", nullable = false, length = 50)
    private String label;

    @Column(name = "text_content", columnDefinition = "TEXT")
    private String textContent;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 关联关系
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Document document;

}
