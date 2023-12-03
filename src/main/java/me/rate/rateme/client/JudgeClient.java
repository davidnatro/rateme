package me.rate.rateme.client;

import java.util.List;
import me.rate.rateme.data.dto.LanguageDto;
import me.rate.rateme.data.dto.SubmissionDto.JudgeSubmissionDto;
import me.rate.rateme.data.dto.SubmissionResponseDto;
import me.rate.rateme.data.dto.SubmissionResultDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "judge-client", url = "${application.judge.url}")
public interface JudgeClient {

  @GetMapping(value = "/languages")
  List<LanguageDto> getLanguages();

  @GetMapping(value = "/submissions/{token}")
  SubmissionResultDto getSubmissionResult(@PathVariable(name = "token") String token);

  @PostMapping("/submissions")
  SubmissionResponseDto submit(@RequestBody JudgeSubmissionDto judgeSubmission);
}
