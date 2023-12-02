package me.rate.rateme.service.impl;

import static me.rate.rateme.data.enums.Status.FAILED;
import static me.rate.rateme.data.enums.Status.PASSED;
import static me.rate.rateme.data.enums.Status.PENDING;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.rate.rateme.client.JudgeClient;
import me.rate.rateme.data.component.SubmissionData;
import me.rate.rateme.data.component.TaskData;
import me.rate.rateme.data.component.UserData;
import me.rate.rateme.data.dto.LanguageDto;
import me.rate.rateme.data.dto.SubmissionDto;
import me.rate.rateme.data.dto.SubmissionDto.JudgeSubmissionDto;
import me.rate.rateme.data.dto.SubmissionResponseDto;
import me.rate.rateme.data.dto.SubmissionResultDto;
import me.rate.rateme.data.dto.SubmissionResultDto.SubmissionStatusDto;
import me.rate.rateme.data.entity.Submission;
import me.rate.rateme.data.entity.Task;
import me.rate.rateme.data.entity.User;
import me.rate.rateme.service.JudgeService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JudgeServiceImpl implements JudgeService {

  private final UserData userData;
  private final TaskData taskData;
  private final JudgeClient judgeClient;
  private final SubmissionData submissionData;

  @Override
  public List<LanguageDto> getLanguages() {
    return judgeClient.getLanguages();
  }

  @Override
  public List<SubmissionResultDto> getSubmissionResult(String token) {
    List<Submission> submissions = submissionData.findByGlobalToken(token);
    List<SubmissionResultDto> submissionResults = new LinkedList<>();

    for (final Submission submission : submissions) {
      SubmissionResultDto resultDto;
      if (submission.isPassed()) {
        resultDto = new SubmissionResultDto(submission.getStdout(), submission.getTime(),
                                            submission.getMemory(), submission.getCompileOutput(),
                                            new SubmissionStatusDto(submission.getStatus().name()));
      } else {
        SubmissionResultDto judgeSubmission = judgeClient.getSubmissionResult(
            submission.getToken());
        submission.setTime(judgeSubmission.time());
        submission.setMemory(judgeSubmission.memory());
        submission.setCompileOutput(judgeSubmission.compileOutput());
        submission.setStdout(judgeSubmission.stdout());
        if (isPassed(judgeSubmission.stdout(), submission.getTestId().toString(),
                     submission.getTask().getOutputData())) {
          submission.setStatus(PASSED);
        } else {
          submission.setStatus(FAILED);
        }

        submissionData.update(submission);
        resultDto = new SubmissionResultDto(judgeSubmission.stdout(), judgeSubmission.time(),
                                            judgeSubmission.memory(),
                                            judgeSubmission.compileOutput(),
                                            new SubmissionStatusDto(submission.getStatus().name()));
      }
      submissionResults.add(resultDto);
    }

    return submissionResults;
  }

  @Override
  public SubmissionResponseDto submit(SubmissionDto submissionDto) {
    Task task = taskData.findById(submissionDto.taskId());
    User user = userData.getUserFromContext();
    String globalToken = generateGlobalToken(task, user);

    for (final Entry<String, Object> entry : task.getInputData().entrySet()) {
      Submission submission = new Submission();
      submission.setUser(user);
      submission.setTask(task);
      submission.setGlobalToken(globalToken);
      submission.setStatus(PENDING);
      submission.setLanguageId(submissionDto.judgeSubmission().languageId());
      submission.setTestId(Long.parseLong(entry.getKey()));

      SubmissionResponseDto submissionResponse = judgeClient.submit(
          new JudgeSubmissionDto(submissionDto.judgeSubmission().sourceCode(),
                                 submissionDto.judgeSubmission().languageId(),
                                 entry.getValue().toString()));

      submission.setToken(submissionResponse.token());
      submissionData.create(submission);
    }

    return new SubmissionResponseDto(globalToken);
  }

  private String generateGlobalToken(Task task, User user) {
    return System.currentTimeMillis() + "-" + task.getId() + "-" + user.getId();
  }

  private boolean isPassed(String stdout, String testId, Map<String, Object> tests) {
    if (!tests.containsKey(testId)) {
      return false;
    }

    return tests.get(testId).equals(stdout);
  }
}
