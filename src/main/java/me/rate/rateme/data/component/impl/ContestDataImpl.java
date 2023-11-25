package me.rate.rateme.data.component.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.rate.rateme.data.component.ContestData;
import me.rate.rateme.data.entity.Contest;
import me.rate.rateme.data.repository.ContestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContestDataImpl implements ContestData {

  private final ContestRepository repository;

  @Override
  public Page<Contest> findAllByCompanyId(Long companyId, Pageable pageable) {
    return repository.findAllByCompanyId(companyId, pageable);
  }

  @Override
  public List<Contest> findAllByCompanyId(Long companyId) {
    return repository.findAllByCompanyId(companyId);
  }

  @Override
  public Contest findById(Long id) {
    Optional<Contest> contest = repository.findById(id);

    if (contest.isEmpty()) {
      log.warn("contest with id '{}' not found", id);
      throw new EntityNotFoundException("contest not found");
    }

    return contest.get();
  }

  @Override
  public Contest findByName(String name) {
    Optional<Contest> contest = repository.findByName(name);

    if (contest.isEmpty()) {
      log.warn("contest '{}' not found", name);
      throw new EntityNotFoundException("contest not found");
    }

    return contest.get();
  }

  @Override
  public Contest create(Contest contest) {
    if (contest.getId() != null) {
      log.warn("contest '{}' already exists", contest.getName());
      throw new EntityExistsException("contest already exists");
    }

    return repository.save(contest);
  }

  @Override
  public Contest update(Contest contest) {
    if (contest.getId() == null) {
      log.warn("contest '{}' not found", contest.getName());
      throw new EntityNotFoundException("contest not found");
    }

    return repository.save(contest);
  }

  @Override
  public void deleteById(Long id) {
    repository.deleteById(id);
  }
}
