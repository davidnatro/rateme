package me.rate.rateme.data.component.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.rate.rateme.data.component.SubmissionData;
import me.rate.rateme.data.entity.Submission;
import me.rate.rateme.data.repository.SubmissionRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubmissionDataImpl implements SubmissionData {

  private final SubmissionRepository repository;

  @Override
  public List<Submission> findByGlobalToken(String globalToken) {
    return repository.findAllByGlobalToken(globalToken);
  }

  @Override
  public Submission create(Submission submission) {
    if (repository.existsByToken(submission.getToken())) {
      log.warn("submission with token '{}' already exists", submission.getToken());
      throw new EntityExistsException("submission already exists");
    }

    return repository.save(submission);
  }

  @Override
  public Submission update(Submission submission) {
    if (!repository.existsByToken(submission.getToken())) {
      log.warn("submission with token '{}' not found", submission.getToken());
      throw new EntityNotFoundException("submission not found");
    }

    return repository.save(submission);
  }
}
