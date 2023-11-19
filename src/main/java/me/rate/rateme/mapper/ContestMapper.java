package me.rate.rateme.mapper;

import me.rate.rateme.data.entity.Contest;
import me.rate.rateme.data.model.ContestModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContestMapper {

    ContestModel toModel(Contest contest);
}
