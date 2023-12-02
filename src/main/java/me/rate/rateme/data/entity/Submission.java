package me.rate.rateme.data.entity;

import static me.rate.rateme.data.enums.Status.PASSED;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.rate.rateme.data.enums.Status;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "submissions")
public class Submission {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "task_id")
  private Task task;

  private String globalToken;

  private String token;

  private Long testId;

  @Enumerated(value = EnumType.STRING)
  private Status status;

  private String stdout;

  private String compileOutput;

  private Double time;

  private Double memory;

  private long languageId;

  @CreationTimestamp
  private ZonedDateTime created;

  @Transient
  public boolean isPassed() {
    return this.status == PASSED;
  }
}
