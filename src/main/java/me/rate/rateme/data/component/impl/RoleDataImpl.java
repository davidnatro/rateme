package me.rate.rateme.data.component.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.rate.rateme.data.component.RoleData;
import me.rate.rateme.data.entity.Role;
import me.rate.rateme.data.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleDataImpl implements RoleData {

  private final RoleRepository repository;

  @Override
  public Page<Role> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Override
  public List<Role> findAll() {
    return repository.findAll();
  }

  @Override
  public Role findByName(String name) {
    Optional<Role> role = repository.findByName(name);

    if (role.isEmpty()) {
      log.warn("role '{}' not found", name);
      throw new EntityNotFoundException("role not found");
    }

    return role.get();
  }

  @Override
  public Role create(Role role) {
    if (repository.existsByName(role.getName())) {
      log.warn("role '{}' already exists", role.getName());
      throw new EntityExistsException("role already exists");
    }

    return repository.save(role);
  }

  @Override
  public Role update(Role role) {
    if (!repository.existsById(role.getId())) {
      log.warn("role with id '{}' not found", role.getId());
      throw new EntityNotFoundException("role not found");
    }

    return repository.save(role);
  }

  @Override
  public void deleteByName(String name) {
    repository.deleteByName(name);
  }
}
