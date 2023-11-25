package me.rate.rateme.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.rate.rateme.data.component.RoleData;
import me.rate.rateme.data.dto.CreateRoleDto;
import me.rate.rateme.data.entity.Role;
import me.rate.rateme.data.model.RoleModel;
import me.rate.rateme.mapper.RoleMapper;
import me.rate.rateme.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleData roleData;
  private final RoleMapper roleMapper;

  @Override
  public Page<RoleModel> findAll(Pageable pageable) {
    return roleData.findAll(pageable).map(roleMapper::toModel);
  }

  @Override
  public RoleModel findByName(String name) {
    return roleMapper.toModel(roleData.findByName(name));
  }

  @Override
  public RoleModel create(CreateRoleDto request) {
    Role role = new Role();
    role.setName(request.name());

    return roleMapper.toModel(roleData.create(role));
  }

  @Override
  public void deleteByName(String name) {
    roleData.deleteByName(name);
  }
}
