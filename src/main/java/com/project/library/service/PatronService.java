package com.project.library.service;

import com.project.library.dto.PageDTO;
import com.project.library.dto.PatronDTO;
import org.springframework.data.domain.Pageable;
import jakarta.validation.Valid;

public interface PatronService {
    PageDTO<PatronDTO> getAllPatrons(Pageable pageable);

    PatronDTO getPatronById(Integer id);

    PatronDTO addPatron(@Valid PatronDTO patron);

    PatronDTO updatePatron(Integer id,@Valid PatronDTO patron);

    void deletePatron(Integer id);
}
