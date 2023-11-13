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

    private final CompanyRepository companyRepository;

    @Override
    public Page<Company> findAll(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company findById(Long id) {
        if (id == null) {
            log.warn("id cannot be null!");
            throw new IllegalArgumentException("cannot find company by null id");
        }

        Optional<Company> company = companyRepository.findById(id);

        if (company.isEmpty()) {
            log.warn("Cannot find company with id '{}'", id);
            throw new EntityNotFoundException("Company not found");
        }

        return company.get();
    }

    @Override
    public Company findByName(String name) {
        if (name == null) {
            log.warn("name cannot be null!");
            throw new IllegalArgumentException("cannot find company by null name");
        }

        Optional<Company> company = companyRepository.findByName(name);

        if (company.isEmpty()) {
            log.warn("Cannot find company with name '{}'", name);
            throw new EntityNotFoundException("Company not found");
        }

        return company.get();
    }

    @Override
    public Company create(Company company) {
        if (company == null) {
            log.warn("Cannot create null company");
            throw new IllegalArgumentException("Company cannot be null during creation!");
        }

        if (company.getId() != null) {
            log.warn("Cannot create company with non null id!");
            throw new IllegalArgumentException("Cannot create company with non null id!");
        }

        if (companyRepository.existsByName(company.getName())) {
            log.warn("Company '{}' already exists", company.getName());
            throw new EntityExistsException("Company already exists");
        }

        return companyRepository.save(company);
    }

    @Override
    public Company update(Company company) {
        if (!companyRepository.existsById(company.getId())) {
            log.warn("Company with id '{}' was not found", company.getId());
            throw new EntityNotFoundException("Company not found");
        }

        return companyRepository.save(company);
    }

    @Override
    public void deleteById(Long id) {
        if (!companyRepository.existsById(id)) {
            log.warn("Company with id '{}' not found", id);
            throw new EntityNotFoundException("Company not found");
        }

        companyRepository.deleteById(id);
    }
}
