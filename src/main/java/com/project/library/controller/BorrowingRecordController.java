package com.project.library.controller;

import com.project.library.dto.BorrowingRecordDTO;
import com.project.library.service.BorrowingRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BorrowingRecordController {

    private final BorrowingRecordService borrowingRecordService;

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDTO> borrowBook(@PathVariable Integer bookId,@PathVariable Integer patronId){
        return ResponseEntity.ok(borrowingRecordService.borrowBook(bookId,patronId));
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDTO> returnBook(@PathVariable Integer bookId,@PathVariable Integer patronId){
        return ResponseEntity.ok(borrowingRecordService.returnBook(bookId,patronId));
    }
}
