package me.rate.rateme.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.rate.rateme.data.dto.CreateContestDto;
import me.rate.rateme.data.dto.CreateTaskDto;
import me.rate.rateme.data.model.ContestModel;
import me.rate.rateme.data.model.TaskModel;
import me.rate.rateme.service.ContestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contest")
@Tag(name = "Contest", description = "contest endpoints")
public class ContestController {

  private final ContestService contestService;

  @GetMapping
  @Operation(summary = "get all company's contests")
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Page of contests"),
      @ApiResponse(responseCode = "401", description = "Unauthorized access") })
  public ResponseEntity<List<ContestModel>> getAllCompanyContests(
      @RequestParam String companyName) {
    return ResponseEntity.ok(contestService.findAllCompanyContests(companyName));
  }

  @GetMapping("/{contestName}/tasks")
  @Operation(summary = "get all contest's tasks")
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Page of contests"),
      @ApiResponse(responseCode = "401", description = "Unauthorized access") })
  public ResponseEntity<List<TaskModel>> getAllCompanyContestTasks(@PathVariable String contestName,
                                                                   @RequestParam String companyName) {
    return ResponseEntity.ok(contestService.findAllContestTasks(companyName, contestName));
  }

  @GetMapping("/{contestName}/tasks/{taskName}")
  @Operation(summary = "get task")
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Task"),
      @ApiResponse(responseCode = "401", description = "Unauthorized access"),
      @ApiResponse(responseCode = "404", description = "Company or contest or task not found") })
  public ResponseEntity<TaskModel> getTask(@PathVariable String contestName,
                                           @PathVariable String taskName,
                                           @RequestParam String companyName) {
    return ResponseEntity.ok(contestService.findContestTask(companyName, contestName, taskName));
  }

  @PostMapping
  @Operation(summary = "create contest")
  @ApiResponses(
      value = { @ApiResponse(responseCode = "200", description = "Created or updated contest"),
          @ApiResponse(responseCode = "401", description = "Unauthorized access"), })
  public ResponseEntity<ContestModel> createContest(@Valid @RequestBody CreateContestDto request,
                                                    @RequestParam String companyName) {

    contestService.createContest(companyName, request);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{contestName}/task")
  @Operation(summary = "create task")
  @ApiResponses(
      value = { @ApiResponse(responseCode = "200", description = "Created or updated task"),
          @ApiResponse(responseCode = "401", description = "Unauthorized access"), })
  public ResponseEntity<Void> createTask(@PathVariable String contestName,
                                         @Valid @RequestBody CreateTaskDto request,
                                         @RequestParam String companyName) {

    contestService.createTask(companyName, contestName, request);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{contestName}/task/{taskName}")
  @Operation(summary = "delete task")
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Deleted task"),
      @ApiResponse(responseCode = "401", description = "Unauthorized access"),
      @ApiResponse(responseCode = "403", description = "Forbidden access"),
      @ApiResponse(responseCode = "404", description = "Company or contest or task not found") })
  public ResponseEntity<Void> deleteTask(@PathVariable String contestName,
                                         @PathVariable String taskName,
                                         @RequestParam String companyName) {

    contestService.deleteTask(companyName, contestName, taskName);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/contest/{contestName}")
  @Operation(summary = "delete contest")
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Deleted contest"),
      @ApiResponse(responseCode = "401", description = "Unauthorized access"),
      @ApiResponse(responseCode = "403", description = "Forbidden access"),
      @ApiResponse(responseCode = "404", description = "Company or contest not found") })
  public ResponseEntity<Void> deleteContest(@PathVariable String contestName,
                                            @RequestParam String companyName) {
    contestService.deleteContest(companyName, contestName);
    return ResponseEntity.noContent().build();
  }
}

