package me.rate.rateme.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.rate.rateme.data.component.CompanyData;
import me.rate.rateme.data.component.UserData;
import me.rate.rateme.data.dto.CreateCompanyDto;
import me.rate.rateme.data.dto.UpdateCompanyDto;
import me.rate.rateme.data.entity.Company;
import me.rate.rateme.data.entity.User;
import me.rate.rateme.data.model.CompanyModel;
import me.rate.rateme.mapper.CompanyMapper;
import me.rate.rateme.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final UserData userData;
    private final CompanyData companyData;
    private final CompanyMapper companyMapper;

    @Override
    public Page<CompanyModel> findAll(Pageable pageable) {
        return companyData.findAll(pageable).map(companyMapper::toModel);
    }

    @Override
    public CompanyModel findByName(String name) {
        return companyMapper.toModel(companyData.findByName(name));
    }

    @Override
    public CompanyModel create(CreateCompanyDto createCompanyDto) {
        Company company = new Company();
        company.setName(createCompanyDto.name());
        company.setEmail(createCompanyDto.email());
        return companyMapper.toModel(companyData.create(company));
    }

    @Override
    public CompanyModel updateByName(String name, UpdateCompanyDto updateCompanyDto) {
        Company company = companyData.findByName(name);

        if (updateCompanyDto.name() != null) {
            company.setName(updateCompanyDto.name());
        }

        if (updateCompanyDto.email() != null) {
            company.setEmail(updateCompanyDto.email());
        }

        return companyMapper.toModel(companyData.update(company));
    }

    @Override
    public void hireEmployee(String name, String username) {
        Company company = companyData.findByName(name);
        User user = userData.findByUsername(username);
        company.getEmployees().add(user);
        companyData.update(company);
    }

    @Override
    public void fireEmployee(String name, String username) {
        Company company = companyData.findByName(name);
        User user = userData.findByUsername(username);
        company.getEmployees().remove(user);
        companyData.update(company);
    }

    @Override
    public void deleteByName(String name) {
        companyData.deleteByName(name);
    }
}
