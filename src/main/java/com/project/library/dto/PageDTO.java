package com.project.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PageDTO<D> {
    private List<D> content;
    private Long totalElements;
    private Integer totalPages;
    private Integer size;
    private Boolean first;
    private Boolean last;
}
