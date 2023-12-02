package me.rate.rateme.data.repository;

import java.util.List;
import me.rate.rateme.data.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

  List<Submission> findAllByGlobalToken(String globalToken);

  boolean existsByToken(String token);
}
