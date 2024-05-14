package com.project.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private  Integer id;

    private String title;

    private  String author;

    private Integer publicationYear;
    @Pattern(regexp = "\\b(?:\\d{10}|\\d{13})\\b", message = "ISBN must have 10 or 13 digits")
    private String isbn;
    @JsonIgnore
    private Boolean deleted;

}
