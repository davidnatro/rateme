package me.rate.rateme.service;

import me.rate.rateme.data.dto.CreateCompanyDto;
import me.rate.rateme.data.dto.CreateContestDto;
import me.rate.rateme.data.dto.UpdateCompanyDto;
import me.rate.rateme.data.entity.Company;
import me.rate.rateme.data.entity.User;
import me.rate.rateme.data.model.CompanyModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

public interface CompanyService {

    Page<CompanyModel> findAll(Pageable pageable);

    CompanyModel findByName(String name);

    CompanyModel create(CreateCompanyDto createCompanyDto);

    CompanyModel updateByName(String companyName, UpdateCompanyDto updateCompanyDto);

    Company checkIfUserHasAccessToCompany(String companyName);

    void checkIfCurrentUserIsHeadOfCompany();

    void hireEmployee(String companyName, String username);

    void fireEmployee(String companyName, String username);

    void deleteByName(String name);
}
