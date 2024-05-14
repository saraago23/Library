package com.project.library.utils;

import com.project.library.dto.PageDTO;
import com.project.library.mapper.BaseMapper;
import org.springframework.data.domain.Page;

import java.util.List;


public class PageUtils {

    public static <E, D> PageDTO<D> toPageImpl(Page<E> page, BaseMapper<E, D> mapper) {
        List<D> dtoList = page.getContent().stream().map(mapper::toDto).toList();
        PageDTO<D> output = new PageDTO<>(dtoList, page.getTotalElements(), page.getTotalPages(), page.getSize(), page.isFirst(), page.isLast());
        return output;
    }

}
