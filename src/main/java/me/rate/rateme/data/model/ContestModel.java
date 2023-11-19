package me.rate.rateme.data.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContestModel {

    private Long id;
    private String name;
    private List<TaskModel> tasks;
}
