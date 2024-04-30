/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ximple.challenge.controller;

import com.ximple.challenge.entity.Book;
import com.ximple.challenge.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ignis
 */
@RestController
@RequestMapping("/books")
public class BookController {
    
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    @GetMapping
    public Page<Book> getAvailableBooksAsPage(Pageable pageable){
        return bookService.getAvailableBooks(pageable);
    }
}
