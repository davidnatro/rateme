package me.rate.rateme.data.repository;

import java.util.Optional;
import me.rate.rateme.data.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    boolean existsByName(String name);

    Optional<Company> findByName(String name);

    void deleteByName(String name);
}
