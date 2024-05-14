package com.project.library.service.impl;

import com.project.library.dto.BorrowingRecordDTO;
import com.project.library.entity.BookEntity;
import com.project.library.entity.BorrowingRecordEntity;
import com.project.library.entity.PatronEntity;
import com.project.library.exceptions.GeneralException;
import com.project.library.repository.BookRepository;
import com.project.library.repository.BorrowingRecordRepository;
import com.project.library.repository.PatronRepository;
import com.project.library.utils.UserUtils;
import com.project.library.service.BorrowingRecordService;
import com.project.library.mapper.BorrowingRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingRecordServiceImpl implements BorrowingRecordService {
    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;
    @Autowired
    private PatronRepository patronRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public BorrowingRecordDTO borrowBook(Integer bookId, Integer patronId) {
        PatronEntity patron = patronRepository.findByIdAndDeletedFalse(patronId).orElseThrow(() -> new GeneralException("No patron with id: " + patronId + " was found on the db"));

        if (!(UserUtils.getLoggedUserRole().contains("ADMIN") || UserUtils.getLoggedUser().equals(patron.getUser().getUsername()))) {
            throw new GeneralException("You can not borrow a book without being a registered user");
        }

        BookEntity bookToBeBorrowed = bookRepository.findByIdAndDeletedFalse(bookId).orElseThrow(() -> new GeneralException("No book with id: " + bookId + " was found on the db"));

        if (borrowingRecordRepository.findBorrowingRecordEntityByReturnDate(null).getBook().equals(bookToBeBorrowed)) {
            throw new GeneralException("This book is borrowed by someone else");
        }

        List<BorrowingRecordEntity> activeBorrowings = borrowingRecordRepository.findBorrowingRecordEntityByPatronAndReturnDate(patron, null);

        if (!activeBorrowings.isEmpty()) {
            throw new GeneralException("Please return the borrowed book before borrowing another one");
        }

        BorrowingRecordEntity newBorrowingRecord = BorrowingRecordEntity.builder()
                .book(bookRepository.findByIdAndDeletedFalse(bookId).orElseThrow(() -> new GeneralException("No book with id: " + bookId + " was found on the db")))
                .patron(patron)
                .borrowDate(LocalDate.now())
                .returnDate(null)
                .deleted(false)
                .build();

        return BorrowingRecordMapper.BORROWING_RECORD_MAPPER.toDto(newBorrowingRecord);
    }

    @Override
    public BorrowingRecordDTO returnBook(Integer bookId, Integer patronId) {
        PatronEntity patron = patronRepository.findByIdAndDeletedFalse(patronId)
                .orElseThrow(() -> new GeneralException("No patron with id: " + patronId + " was found on the db"));

        BookEntity bookToBeReturned = bookRepository.findByIdAndDeletedFalse(bookId)
                .orElseThrow(() -> new GeneralException("No book with id: " + bookId + " was found on the db"));

        BorrowingRecordEntity borrowingRecord = borrowingRecordRepository
                .findFirstByBookAndPatronAndReturnDateIsNull(bookToBeReturned, patron)
                .orElseThrow(() -> new GeneralException("No active borrowing record found for the specified book and patron"));

        borrowingRecord.setReturnDate(LocalDate.now());
        borrowingRecordRepository.save(borrowingRecord);

        return BorrowingRecordMapper.BORROWING_RECORD_MAPPER.toDto(borrowingRecord);
    }
}
