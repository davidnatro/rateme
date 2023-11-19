package me.rate.rateme.service;

import java.util.List;
import me.rate.rateme.data.dto.CreateContestDto;
import me.rate.rateme.data.dto.CreateTaskDto;
import me.rate.rateme.data.model.ContestModel;
import me.rate.rateme.data.model.TaskModel;

public interface ContestService {

    List<ContestModel> findAllCompanyContests(String companyName);

    List<TaskModel> findAllContestTasks(String companyName, String contestName);

    TaskModel findContestTask(String companyName, String contestName, String taskName);

    void createContest(String companyName, CreateContestDto createContestDto);

    void createTask(String companyName, String contestName, CreateTaskDto createTaskDto);

    void deleteContest(String companyName, String contestName);

    void deleteTask(String companyName, String contestName, String taskName);
}
