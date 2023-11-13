package me.rate.rateme.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.rate.rateme.data.component.RoleData;
import me.rate.rateme.data.component.UserData;
import me.rate.rateme.data.dto.CreateUserDto;
import me.rate.rateme.data.dto.UpdateUserDto;
import me.rate.rateme.data.entity.User;
import me.rate.rateme.data.model.UserModel;
import me.rate.rateme.mapper.UserMapper;
import me.rate.rateme.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserData userData;
    private final RoleData roleData;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<UserModel> findAll(Pageable pageable) {
        return userData.findAll(pageable).map(userMapper::toModel);
    }

    @Override
    public UserModel create(CreateUserDto request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toModel(userData.create(user));
    }

    @Override
    public UserModel updateById(Long id, UpdateUserDto request) {
        User user = userData.findById(id);

        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }

        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            user.getRoles().clear();
            request.getRoles().forEach(r -> user.getRoles().add(roleData.findByName(r)));
        }

        return userMapper.toModel(userData.update(user));
    }

    @Override
    public void deleteById(Long id) {
        userData.deleteById(id);
    }
}
