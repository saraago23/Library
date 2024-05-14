package com.project.library.service;

import com.project.library.dto.BookDTO;
import com.project.library.dto.PageDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

public interface BookService {

    PageDTO<BookDTO> getAllBooks(Pageable pageable);

    BookDTO getBookById(Integer id);

    BookDTO addBook(@Valid BookDTO book);

    BookDTO updateBook(Integer id,@Valid BookDTO book);

    void deleteBook(Integer id);
}
