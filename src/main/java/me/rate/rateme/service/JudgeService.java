package me.rate.rateme.service;

import java.util.List;
import me.rate.rateme.data.dto.LanguageDto;
import me.rate.rateme.data.dto.SubmissionDto;
import me.rate.rateme.data.dto.SubmissionResponseDto;
import me.rate.rateme.data.dto.SubmissionResultDto;

public interface JudgeService {

  List<LanguageDto> getLanguages();

  List<SubmissionResultDto> getSubmissionResult(String token);

  SubmissionResponseDto submit(SubmissionDto submission);
}
