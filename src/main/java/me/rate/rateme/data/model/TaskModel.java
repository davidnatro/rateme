package me.rate.rateme.data.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.rate.rateme.data.enums.Difficulty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskModel {

  private Long id;
  private String name;
  private String pictureKey;
  private String description;
  private String inputFormat;
  private String outputFormat;
  private String codeExample;
  private Difficulty difficulty;
  private Map<String, Object> inputData;
  private Map<String, Object> outputData;
}
