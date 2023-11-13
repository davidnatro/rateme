package me.rate.rateme.data.repository;

import java.util.List;
import java.util.Optional;
import me.rate.rateme.data.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAllByContestId(Long contestId, Pageable pageable);

    List<Task> findAllByContestId(Long contestId);

    Optional<Task> findByName(String name);
}
