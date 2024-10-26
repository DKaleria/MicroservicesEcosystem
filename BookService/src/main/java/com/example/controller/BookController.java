package com.example.controller;

import com.example.controller.request.BookCreateRequest;
import com.example.controller.request.BookUpdateRequest;
import com.example.mapper.BookMapper;
import com.example.model.BookModel;
import com.example.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping
    public ResponseEntity<List<BookModel>> getBooks(
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) String authorSurname,
            @RequestParam(required = false) String genreName) {
        List<BookModel> books = bookService.getBooks(authorName, authorSurname, genreName);
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<BookModel> addBook(@RequestBody BookCreateRequest request) {
        BookModel model = bookMapper.toModel(request);
        model = bookService.addBook(model);
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @PutMapping("/{book_id}")
    public ResponseEntity<BookModel> updateBook(@PathVariable(name = "book_id") Long bookId, @RequestBody BookUpdateRequest request) {
        BookModel model = bookMapper.toModel(request);
        model = bookService.updateBook(bookId, model);
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{book_id}")
    public ResponseEntity<Void> deleteBook(@PathVariable(name = "book_id") Long bookId) {
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
