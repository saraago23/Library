package com.project.library.service;

import com.project.library.dto.BorrowingRecordDTO;

public interface BorrowingRecordService {

    BorrowingRecordDTO borrowBook(Integer bookId, Integer patronId);


    BorrowingRecordDTO returnBook(Integer bookId, Integer patronId);
}
