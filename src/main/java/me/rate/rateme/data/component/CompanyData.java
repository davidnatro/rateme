package me.rate.rateme.data.component;

import java.util.List;
import me.rate.rateme.data.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyData {

    Page<Company> findAll(Pageable pageable);

    List<Company> findAll();

    Company findById(Long id);

    Company findByName(String name);

    Company create(Company company);

    Company update(Company company);

    void deleteByName(String name);
}
