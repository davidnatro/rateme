package me.rate.rateme.mapper;

import me.rate.rateme.data.dto.CreateTaskDto;
import me.rate.rateme.data.entity.Task;
import me.rate.rateme.data.model.TaskModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {

  TaskModel toModel(Task task);

  @BeanMapping(
      nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(@MappingTarget Task task, CreateTaskDto dto);
}
