package me.rate.rateme.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.rate.rateme.data.component.CompanyData;
import me.rate.rateme.data.component.UserData;
import me.rate.rateme.data.dto.CreateCompanyDto;
import me.rate.rateme.data.dto.CreateContestDto;
import me.rate.rateme.data.dto.UpdateCompanyDto;
import me.rate.rateme.data.entity.Company;
import me.rate.rateme.data.entity.Contest;
import me.rate.rateme.data.entity.User;
import me.rate.rateme.data.model.CompanyModel;
import me.rate.rateme.mapper.CompanyMapper;
import me.rate.rateme.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public CompanyModel updateByName(String companyName, UpdateCompanyDto updateCompanyDto) {
        Company company = checkIfUserHasAccessToCompany(companyName);
        checkIfUserIsHeadOfCompany();

        if (updateCompanyDto.name() != null) {
            company.setName(updateCompanyDto.name());
        }

        if (updateCompanyDto.email() != null) {
            company.setEmail(updateCompanyDto.email());
        }

        return companyMapper.toModel(companyData.update(company));
    }

    @Override
    public void hireEmployee(String companyName, String username) {
        Company company = checkIfUserHasAccessToCompany(companyName);
        checkIfUserIsHeadOfCompany();
        User user = userData.findByUsername(username);
        company.getEmployees().add(user);
        companyData.update(company);
    }

    @Override
    public void fireEmployee(String companyName, String username) {
        Company company = checkIfUserHasAccessToCompany(companyName);
        checkIfUserIsHeadOfCompany();
        User user = userData.findByUsername(username);
        company.getEmployees().remove(user);
        companyData.update(company);
    }

    @Override
    public void createContest(String companyName, CreateContestDto createContestDto) {
        Company company = checkIfUserHasAccessToCompany(companyName);

        Contest contest = new Contest();
        contest.setName(createContestDto.name());
        contest.setCompany(company);

        company.getContests().add(contest);
        companyData.update(company);
    }

    @Override
    public void deleteContest(String companyName, String contestName) {
        Company company = checkIfUserHasAccessToCompany(companyName);

        company.getContests().removeIf(contest -> contest.getName().equals(contestName));
        companyData.update(company);
    }

    @Override
    public void deleteByName(String name) {
        checkIfUserIsHeadOfCompany();
        companyData.deleteByName(name);
    }

    private Company checkIfUserHasAccessToCompany(String companyName) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Company company = companyData.findByName(companyName);

        if (company.getEmployees()
                .stream()
                .noneMatch(u -> u.getUsername().equals(user.getUsername()))) {
            log.warn("User {} is not an employee of company {} trying to create contest",
                     user.getUsername(), companyName);
            throw new AccessDeniedException("You are not an employee of this company");
        }

        return company;
    }

    private void checkIfUserIsHeadOfCompany() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!user.isHeadOfCompany()) {
            log.warn("User {} is not a head of company trying to access forbidden action",
                     user.getUsername());
            throw new AccessDeniedException("You are not a head of company");
        }
    }
}
