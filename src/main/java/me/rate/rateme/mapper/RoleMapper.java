package me.rate.rateme.mapper;

import me.rate.rateme.data.entity.Role;
import me.rate.rateme.data.model.RoleModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

  RoleModel toModel(Role role);
}
