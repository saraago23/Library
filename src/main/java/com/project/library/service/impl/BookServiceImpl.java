package com.project.library.service.impl;

import com.project.library.dto.BookDTO;
import com.project.library.dto.PageDTO;
import com.project.library.entity.BookEntity;
import com.project.library.exceptions.GeneralException;
import com.project.library.repository.BookRepository;
import com.project.library.service.BookService;
import com.project.library.utils.PageUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


import static com.project.library.mapper.BookMapper.*;

@Service
@Validated
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public PageDTO<BookDTO> getAllBooks(Pageable pageable) {
        return PageUtils.toPageImpl(bookRepository.findAllNonDeleted(pageable), BOOK_MAPPER);
    }

    @Override
    public BookDTO getBookById(Integer id) {
        BookEntity bookEntity = bookRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("Book with id: " + id + " was not found"));

        return BOOK_MAPPER.toDto(bookEntity);
    }

    @Override
    public BookDTO addBook(@Valid BookDTO book) {
        if (bookRepository.findByIdAndDeletedFalse(book.getId()).isPresent()) {
            throw new GeneralException("There already is a book with this id on the db");
        }

        BookEntity newBook = BookEntity.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publicationYear(book.getPublicationYear())
                .deleted(false)
                .build();

        bookRepository.save(newBook);

        return BOOK_MAPPER.toDto(newBook);
    }

    @Override
    public BookDTO updateBook(Integer id,@Valid BookDTO book) {

        BookEntity bookToBeUpdated = bookRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("No book with id: " + book.getId() + " was found on the db"));

        BookDTO updatedBook = BOOK_MAPPER.toDto(bookToBeUpdated);
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setTitle(book.getTitle());
        updatedBook.setIsbn(bookToBeUpdated.getIsbn());
        updatedBook.setPublicationYear(book.getPublicationYear());

        return BOOK_MAPPER.toDto(bookRepository.save(BOOK_MAPPER.toEntity(updatedBook)));
    }

    @Override
    public void deleteBook(Integer id) {
        BookEntity bookToBeDeleted = bookRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("No book with id: " + id + " was found on the db"));

        BookDTO deletedBook=BOOK_MAPPER.toDto(bookToBeDeleted);

        deletedBook.setDeleted(true);

        bookRepository.save(BOOK_MAPPER.toEntity(deletedBook));

    }
}
