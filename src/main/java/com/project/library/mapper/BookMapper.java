package com.project.library.mapper;

import com.project.library.dto.BookDTO;
import com.project.library.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface BookMapper extends BaseMapper<BookEntity, BookDTO>{
    BookMapper BOOK_MAPPER= Mappers.getMapper(BookMapper.class);
}
