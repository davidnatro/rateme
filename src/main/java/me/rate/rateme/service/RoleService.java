package me.rate.rateme.service;

import me.rate.rateme.data.dto.CreateRoleDto;
import me.rate.rateme.data.model.RoleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {

    Page<RoleModel> findAll(Pageable pageable);

    RoleModel findByName(String name);

    RoleModel create(CreateRoleDto request);

    void deleteByName(String name);
}
