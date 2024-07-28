package com.perpus.book.controller;


import com.perpus.book.dto.in.BookRequest;
import com.perpus.book.dto.out.BookResult;
import com.perpus.book.model.Book;
import com.perpus.book.service.BookService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/list-all")
    public ResponseEntity<List<BookResult>> getAll() {
        return ResponseEntity.ok(bookService.getAllBook());
    }

    @PostMapping("")
    public ResponseEntity<String> create(@RequestBody BookRequest request) throws Exception {
        bookService.insertBook(request);
        return ResponseEntity.ok("Success Insert Book");
    }

    @PutMapping("/{ID}")
    public ResponseEntity<String>update(@PathVariable("ID")Long id,@RequestBody BookRequest request) throws Exception {
        bookService.updateBook(request,id);
        return ResponseEntity.ok("Success Update Book");
    }
}
