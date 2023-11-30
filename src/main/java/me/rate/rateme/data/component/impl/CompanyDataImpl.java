package me.rate.rateme.data.component.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.rate.rateme.data.component.CompanyData;
import me.rate.rateme.data.entity.Company;
import me.rate.rateme.data.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompanyDataImpl implements CompanyData {

  private final CompanyRepository repository;

  @Override
  public Page<Company> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Override
  public List<Company> findAll() {
    return repository.findAll();
  }

  @Override
  public Company findById(Long id) {
    Optional<Company> company = repository.findById(id);

    if (company.isEmpty()) {
      log.warn("company with id '{}' not found", id);
      throw new EntityNotFoundException("company not found");
    }

    return company.get();
  }

  @Override
  public Company findByName(String name) {
    Optional<Company> company = repository.findByName(name);

    if (company.isEmpty()) {
      log.warn("company '{}' not found", name);
      throw new EntityNotFoundException("company not found");
    }

    return company.get();
  }

  @Override
  public Company create(Company company) {
    if (repository.existsByName(company.getName())) {
      log.warn("company '{}' already exists", company.getName());
      throw new EntityExistsException("company already exists");
    }

    return repository.save(company);
  }

  @Override
  public Company update(Company company) {
    if (!repository.existsById(company.getId())) {
      log.warn("company with id '{}' not found", company.getId());
      throw new EntityNotFoundException("company not found");
    }

    return repository.save(company);
  }

  @Override
  public void deleteByName(String name) {
    repository.deleteByName(name);
  }
}
