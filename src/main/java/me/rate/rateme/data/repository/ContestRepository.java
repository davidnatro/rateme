package me.rate.rateme.data.repository;

import java.util.List;
import java.util.Optional;
import me.rate.rateme.data.entity.Contest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {

    Page<Contest> findAllByCompanyId(Long companyId, Pageable pageable);

    List<Contest> findAllByCompanyId(Long companyId);

    Optional<Contest> findByName(String name);
}
