package me.rate.rateme.data.component;

import java.util.List;
import me.rate.rateme.data.entity.Submission;

public interface SubmissionData {

  List<Submission> findByGlobalToken(String globalToken);

  Submission create(Submission submission);

  Submission update(Submission submission);
}
