package com.project.library.mapper;

import com.project.library.dto.BorrowingRecordDTO;
import com.project.library.entity.BorrowingRecordEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface BorrowingRecordMapper extends BaseMapper<BorrowingRecordEntity, BorrowingRecordDTO> {
    BorrowingRecordMapper BORROWING_RECORD_MAPPER= Mappers.getMapper(BorrowingRecordMapper.class);
}
