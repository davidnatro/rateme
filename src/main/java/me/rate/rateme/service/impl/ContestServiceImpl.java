package me.rate.rateme.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.rate.rateme.data.component.CompanyData;
import me.rate.rateme.data.dto.CreateContestDto;
import me.rate.rateme.data.dto.CreateTaskDto;
import me.rate.rateme.data.entity.Company;
import me.rate.rateme.data.entity.Contest;
import me.rate.rateme.data.entity.Task;
import me.rate.rateme.data.model.ContestModel;
import me.rate.rateme.data.model.TaskModel;
import me.rate.rateme.mapper.TaskMapper;
import me.rate.rateme.service.CompanyService;
import me.rate.rateme.service.ContestService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContestServiceImpl implements ContestService {

    private final TaskMapper taskMapper;
    private final CompanyData companyData;
    private final CompanyService companyService;

    @Override
    public List<ContestModel> findAllCompanyContests(String companyName) {
        return companyService.findByName(companyName).getContests();
    }

    @Override
    public List<TaskModel> findAllContestTasks(String companyName, String contestName) {
        return companyService.findByName(companyName)
                .getContests()
                .stream()
                .filter(contest -> contest.getName().equals(contestName))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Contest not found"))
                .getTasks();
    }

    @Override
    public TaskModel findContestTask(String companyName, String contestName, String taskName) {
        return this.findAllContestTasks(companyName, contestName)
                .stream()
                .filter(f -> f.getName().equals(taskName))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    @Override
    public void createContest(String companyName, CreateContestDto createContestDto) {
        Company company = companyService.checkIfUserHasAccessToCompany(companyName);

        Contest contest = new Contest();
        contest.setName(createContestDto.name());
        contest.setCompany(company);

        company.getContests().add(contest);
        companyData.update(company);
    }

    @Override
    public void deleteContest(String companyName, String contestName) {
        Company company = companyService.checkIfUserHasAccessToCompany(companyName);

        company.getContests().removeIf(contest -> contest.getName().equals(contestName));
        companyData.update(company);
    }

    @Override
    public void createTask(String companyName, String contestName, CreateTaskDto createTaskDto) {
        Company company = companyService.checkIfUserHasAccessToCompany(companyName);

        Optional<Contest> contest = company.getContests()
                .stream()
                .filter(c -> c.getName().equals(contestName))
                .findFirst();

        if (contest.isEmpty()) {
            log.warn("Contest '{}' for company '{}' not found. Unable to create new task",
                     contestName, companyName);
            throw new EntityNotFoundException("Contest not found");
        }

        Task task = new Task();
        task.setContest(contest.get());
        taskMapper.updateEntity(task, createTaskDto);

        contest.get().getTasks().remove(task);
        contest.get().getTasks().add(task);

        companyData.update(company);
    }

    @Override
    public void deleteTask(String companyName, String contestName, String taskName) {
        Company company = companyService.checkIfUserHasAccessToCompany(companyName);

        Optional<Contest> contest = company.getContests()
                .stream()
                .filter(c -> c.getName().equals(contestName))
                .findFirst();

        if (contest.isEmpty()) {
            log.warn("Contest '{}' for company '{}' not found. Unable to delete task '{}'",
                     contestName, companyName, taskName);
            throw new EntityNotFoundException("Contest not found");
        }

        boolean removed = contest.get()
                .getTasks()
                .removeIf(task -> task.getName().equals(taskName));

        if (!removed) {
            log.warn("Task '{}' for contest '{}' for company '{}' not found. Unable to delete task",
                     taskName, contestName, companyName);
            throw new EntityNotFoundException("Task not found");
        }

        companyData.update(company);
    }
}
