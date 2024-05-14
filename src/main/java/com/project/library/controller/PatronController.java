package com.project.library.controller;

import com.project.library.dto.PageDTO;
import com.project.library.dto.PatronDTO;
import com.project.library.service.PatronService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patrons")
@RequiredArgsConstructor
public class PatronController {

    private final PatronService patronService;

    @GetMapping
    public ResponseEntity<PageDTO<PatronDTO>> getAllPatrons(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                            @RequestParam(required = false,defaultValue = "10") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return ResponseEntity.ok(patronService.getAllPatrons(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatronDTO> getPatronById(@PathVariable Integer id){
        return ResponseEntity.ok(patronService.getPatronById(id));
    }

    @PostMapping
    public ResponseEntity<PatronDTO> addPatron(@RequestBody PatronDTO patron){
        return ResponseEntity.ok(patronService.addPatron(patron));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatronDTO> updatePatron(@PathVariable Integer id,@RequestBody PatronDTO patron){
        return ResponseEntity.ok(patronService.updatePatron(id,patron));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Integer id){
        patronService.deletePatron(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
