package com.project.library.dto;

import com.project.library.entity.BookEntity;
import com.project.library.entity.PatronEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingRecordDTO {

    private Integer id;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private BookEntity book;
    private PatronEntity patron;
    @JsonIgnore
    private Boolean deleted;
}
