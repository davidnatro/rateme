package me.rate.rateme.data.component;

import java.util.List;
import me.rate.rateme.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserData extends UserDetailsService {

    Page<User> findAll(Pageable pageable);

    List<User> findAll();

    User findByUsername(String username);

    User findById(Long id);

    User create(User user);

    User update(User user);

    void deleteById(Long id);
}
