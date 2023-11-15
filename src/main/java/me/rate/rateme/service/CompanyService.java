package me.rate.rateme.service;

import me.rate.rateme.data.dto.CreateCompanyDto;
import me.rate.rateme.data.dto.UpdateCompanyDto;
import me.rate.rateme.data.model.CompanyModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {

    Page<CompanyModel> findAll(Pageable pageable);

    CompanyModel findByName(String name);

    CompanyModel create(CreateCompanyDto createCompanyDto);

    CompanyModel updateByName(String name, UpdateCompanyDto updateCompanyDto);

    void hireEmployee(String name, String username);

    void fireEmployee(String name, String username);

    void deleteByName(String name);
}
