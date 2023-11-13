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

    private final TaskRepository taskRepository;

    @Override
    public Page<Task> findAllByContestId(Long contestId, Pageable pageable) {
        return taskRepository.findAllByContestId(contestId, pageable);
    }

    @Override
    public Page<Task> findAllWithoutContest(Pageable pageable) {
        return taskRepository.findAllByContestIdIsNull(pageable);
    }

    @Override
    public List<Task> findAllByContestId(Long contestId) {
        return taskRepository.findAllByContestId(contestId);
    }

    @Override
    public List<Task> findAllWithoutContest() {
        return taskRepository.findAllByContestIdIsNull();
    }

    @Override
    public Task findById(Long id) {
        if (id == null) {
            log.warn("id cannot be null!");
            throw new IllegalArgumentException("cannot find task by null id");
        }

        Optional<Task> task = taskRepository.findById(id);

        if (task.isEmpty()) {
            log.warn("Cannot find task with id '{}'", id);
            throw new EntityNotFoundException("Task not found");
        }

        return task.get();
    }

    @Override
    public Task findByName(String name) {
        if (name == null) {
            log.warn("name cannot be null!");
            throw new IllegalArgumentException("cannot find task by null name");
        }

        Optional<Task> task = taskRepository.findByName(name);

        if (task.isEmpty()) {
            log.warn("Cannot find task with name '{}'", name);
            throw new EntityNotFoundException("Task not found");
        }

        return task.get();
    }

    @Override
    public Task create(Task task) {
        if (task == null) {
            log.warn("Cannot create null task");
            throw new IllegalArgumentException("task cannot be null during creation!");
        }

        if (task.getId() != null) {
            log.warn("Cannot create task with non null id!");
            throw new IllegalArgumentException("Cannot create task with non null id!");
        }

        if (isTaskAlreadyExists(task)) {
            log.warn("task with name '{}' already exists", task.getName());
            throw new EntityExistsException("task already exists");
        }

        return taskRepository.save(task);
    }

    @Override
    public Task update(Task task) {
        if (!taskRepository.existsById(task.getId())) {
            log.warn("task with id '{}' was not found", task.getId());
            throw new EntityNotFoundException("Task not found");
        }

        return taskRepository.save(task);
    }

    @Override
    public void deleteById(Long id) {
        if (!taskRepository.existsById(id)) {
            log.warn("task with id '{}' not found", id);
            throw new EntityNotFoundException("Task not found");
        }

        taskRepository.deleteById(id);
    }

    private boolean isTaskAlreadyExists(Task task) {
        return task.getContest() == null && taskRepository.existsByName(task.getName())
                || task.getContest() != null && task.getContest().getTasks().contains(task);
    }
}
