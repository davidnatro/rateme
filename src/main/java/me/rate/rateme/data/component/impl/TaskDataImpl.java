package me.rate.rateme.data.component.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.rate.rateme.data.component.TaskData;
import me.rate.rateme.data.entity.Task;
import me.rate.rateme.data.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskDataImpl implements TaskData {

    private final TaskRepository repository;

    @Override
    public Page<Task> findAllByContestId(Long contestId, Pageable pageable) {
        return repository.findAllByContestId(contestId, pageable);
    }

    @Override
    public List<Task> findAllByContestId(Long contestId) {
        return repository.findAllByContestId(contestId);
    }

    @Override
    public Task findById(Long id) {
        Optional<Task> task = repository.findById(id);

        if (task.isEmpty()) {
            log.warn("task with id '{}' not found", id);
            throw new EntityNotFoundException("task not found");
        }

        return task.get();
    }

    @Override
    public Task findByName(String name) {
        Optional<Task> task = repository.findByName(name);

        if (task.isEmpty()) {
            log.warn("task '{}' not found", name);
            throw new EntityNotFoundException("task not found");
        }

        return task.get();
    }

    @Override
    public Task create(Task task) {
        if (task.getId() != null) {
            log.warn("task '{}' already exists", task.getName());
            throw new EntityExistsException("task already exists");
        }

        return repository.save(task);
    }

    @Override
    public Task update(Task task) {
        if (task.getId() == null) {
            log.warn("task '{}' not found", task.getName());
            throw new EntityNotFoundException("task not found");
        }

        return repository.save(task);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
