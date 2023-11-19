package me.rate.rateme.service;

import me.rate.rateme.data.dto.CreateUserDto;
import me.rate.rateme.data.dto.UpdateUserDto;
import me.rate.rateme.data.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<UserModel> findAll(Pageable pageable);

    UserModel findByUsername(String username);

    UserModel create(CreateUserDto request);

    UserModel updateByUsername(String username, UpdateUserDto request);

    void deleteByUsername(String username);
}
