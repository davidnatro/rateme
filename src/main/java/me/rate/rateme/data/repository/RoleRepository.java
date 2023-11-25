package me.rate.rateme.data.repository;

import java.util.Optional;
import me.rate.rateme.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(String name);

  boolean existsByName(String name);

  void deleteByName(String name);
}
