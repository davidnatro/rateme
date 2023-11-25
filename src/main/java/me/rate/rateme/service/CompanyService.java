package me.rate.rateme.service;

import me.rate.rateme.data.dto.CreateCompanyDto;
import me.rate.rateme.data.dto.UpdateCompanyDto;
import me.rate.rateme.data.entity.Company;
import me.rate.rateme.data.model.CompanyModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {

  Page<CompanyModel> findAll(Pageable pageable);

  CompanyModel findByName(String name);

  Company findByNameEntity(String name);

  CompanyModel create(CreateCompanyDto createCompanyDto);

  CompanyModel updateByName(String companyName, UpdateCompanyDto updateCompanyDto);

  CompanyModel update(Company company);

  Company checkIfUserHasAccessToCompany(String companyName);

  void checkIfCurrentUserIsHeadOfCompany();

  void hireEmployee(String companyName, String username);

  void fireEmployee(String companyName, String username);

  void deleteByName(String name);
}
