package me.rate.rateme.mapper;

import me.rate.rateme.data.entity.User;
import me.rate.rateme.data.model.UserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserModel toModel(User user);
}
