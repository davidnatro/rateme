package me.rate.rateme.data.component;

import java.util.List;
import me.rate.rateme.data.entity.Contest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContestData {

    Page<Contest> findAllByCompanyId(Long companyId, Pageable pageable);

    List<Contest> findAllByCompanyId(Long companyId);

    Contest findById(Long id);

    Contest findByName(String name);

    Contest create(Contest contest);

    Contest update(Contest contest);

    void deleteById(Long id);
}
