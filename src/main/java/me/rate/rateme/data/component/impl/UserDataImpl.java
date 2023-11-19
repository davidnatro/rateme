package me.rate.rateme.data.component.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.rate.rateme.data.component.UserData;
import me.rate.rateme.data.entity.User;
import me.rate.rateme.data.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDataImpl implements UserData {

    private final UserRepository repository;

    @Override
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = repository.findByUsername(username);

        if (user.isEmpty()) {
            log.warn("user '{}' not found", username);
            throw new EntityNotFoundException("user not found");
        }

        return user.get();
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = repository.findById(id);

        if (user.isEmpty()) {
            log.warn("user with id '{}' not found", id);
            throw new EntityNotFoundException("user not found");
        }

        return user.get();
    }

    @Override
    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            log.warn("user '{}' already exists", user.getUsername());
            throw new EntityExistsException("user already exists");
        }

        return repository.save(user);
    }

    @Override
    public User update(User user) {
        if (!repository.existsById(user.getId())) {
            log.warn("user with id '{}' not found", user.getId());
            throw new EntityNotFoundException("user not found");
        }

        return repository.save(user);
    }

    @Override
    public void deleteByUsername(String username) {
        repository.deleteByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = repository.findByUsername(username);

        if (userOptional.isEmpty()) {
            log.warn("user '{}' not found", username);
            throw new UsernameNotFoundException("user not found");
        }

        return userOptional.get();
    }
}
