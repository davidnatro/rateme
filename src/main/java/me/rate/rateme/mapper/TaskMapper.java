package me.rate.rateme.mapper;

import me.rate.rateme.data.entity.Task;
import me.rate.rateme.data.model.TaskModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskModel toModel(Task task);
}
