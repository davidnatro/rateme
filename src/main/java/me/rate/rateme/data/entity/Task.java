package me.rate.rateme.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import me.rate.rateme.data.enums.Difficulty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String pictureKey;

    private String description;

    private String inputFormat;

    private String outputFormat;

    private String codeExample;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> inputData;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> outputData;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    @CreationTimestamp
    private ZonedDateTime created;

    @UpdateTimestamp
    private ZonedDateTime modified;
}
