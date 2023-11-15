package me.rate.rateme.data.component;

import java.util.List;
import me.rate.rateme.data.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleData {

    Page<Role> findAll(Pageable pageable);

    List<Role> findAll();

    Role findByName(String name);

    Role create(Role role);

    Role update(Role role);

    void deleteByName(String name);
}
