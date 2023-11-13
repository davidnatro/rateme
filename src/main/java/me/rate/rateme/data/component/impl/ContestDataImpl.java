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

    private final ContestRepository contestRepository;

    @Override
    public Page<Contest> findAllByCompanyId(Long companyId, Pageable pageable) {
        return contestRepository.findAllByCompanyId(companyId, pageable);
    }

    @Override
    public Page<Contest> findAllWithoutCompany(Pageable pageable) {
        return contestRepository.findAllByCompanyIdIsNull(pageable);
    }

    @Override
    public List<Contest> findAllByCompanyId(Long companyId) {
        return contestRepository.findAllByCompanyId(companyId);
    }

    @Override
    public List<Contest> findAllWithoutCompany() {
        return contestRepository.findAllByCompanyIdIsNull();
    }

    @Override
    public Contest findById(Long id) {
        if (id == null) {
            log.warn("id cannot be null!");
            throw new IllegalArgumentException("cannot find contest by null id");
        }

        Optional<Contest> contest = contestRepository.findById(id);

        if (contest.isEmpty()) {
            log.warn("Cannot find contest with id '{}'", id);
            throw new EntityNotFoundException("Contest not found");
        }

        return contest.get();
    }

    @Override
    public Contest findByName(String name) {
        if (name == null) {
            log.warn("name cannot be null!");
            throw new IllegalArgumentException("cannot find contest by null name");
        }

        Optional<Contest> contest = contestRepository.findByName(name);

        if (contest.isEmpty()) {
            log.warn("Cannot find contest with name '{}'", name);
            throw new EntityNotFoundException("Contest not found");
        }

        return contest.get();
    }

    @Override
    public Contest create(Contest contest) {
        if (contest == null) {
            log.warn("Cannot create null contest");
            throw new IllegalArgumentException("contest cannot be null during creation!");
        }

        if (contest.getId() != null) {
            log.warn("Cannot create contest with non null id!");
            throw new IllegalArgumentException("Cannot create contest with non null id!");
        }

        if (isContestAlreadyExists(contest)) {
            log.warn("Contest '{}' already exists", contest.getName());
            throw new EntityExistsException("Contest already exists");
        }

        return contestRepository.save(contest);
    }

    @Override
    public Contest update(Contest contest) {
        if (!contestRepository.existsById(contest.getId())) {
            log.warn("Contest with id '{}' was not found", contest.getId());
            throw new EntityNotFoundException("Contest not found");
        }

        return contestRepository.save(contest);
    }

    @Override
    public void deleteById(Long id) {
        if (!contestRepository.existsById(id)) {
            log.warn("Contest with id '{}' not found", id);
            throw new EntityNotFoundException("Contest not found");
        }

        contestRepository.deleteById(id);
    }

    private boolean isContestAlreadyExists(Contest contest) {
        return contest.getCompany() == null && contestRepository.existsById(contest.getId())
                || contest.getCompany() != null && contest.getCompany()
                .getContests()
                .contains(contest);
    }
}
