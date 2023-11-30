package me.rate.rateme.mapper;

import me.rate.rateme.data.entity.Company;
import me.rate.rateme.data.model.CompanyModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

  CompanyModel toModel(Company company);
}
