package me.rate.rateme.service;

import java.util.List;
import me.rate.rateme.data.dto.LanguageDto;
import me.rate.rateme.data.dto.SubmissionDto;

public interface JudgeService {

  List<LanguageDto> getLanguages();

  boolean checkSolution();
}
