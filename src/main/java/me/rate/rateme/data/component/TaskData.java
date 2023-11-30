package me.rate.rateme.data.component;

import java.util.List;
import me.rate.rateme.data.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskData {

  Page<Task> findAllByContestId(Long contestId, Pageable pageable);

  List<Task> findAllByContestId(Long contestId);

  Task findById(Long id);

  Task findByName(String name);

  Task create(Task task);

  Task update(Task task);

  void deleteById(Long id);
}
