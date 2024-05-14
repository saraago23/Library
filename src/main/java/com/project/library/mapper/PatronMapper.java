package com.project.library.mapper;

import com.project.library.dto.PatronDTO;
import com.project.library.entity.PatronEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface PatronMapper extends BaseMapper<PatronEntity, PatronDTO>{
    PatronMapper PATRON_MAPPER= Mappers.getMapper(PatronMapper.class);
}
