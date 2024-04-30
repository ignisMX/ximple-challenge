/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ximple.challenge.service;

import com.ximple.challenge.entity.Book;
import com.ximple.challenge.repository.BookRepository;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author ignis
 */
@Service
public class BookService {
    
    private final BookRepository bookRepository;
    
    private final ValidationService validationService;

    public BookService(BookRepository bookRepository, ValidationService validationService) {
        this.bookRepository = bookRepository;
        this.validationService = validationService;
    }
    
    public Page<Book> getAvailableBooks(Pageable pageable){
        
        return bookRepository.findByAvailableTrue(pageable);
    }
    
    public Book markAsUnavailable(Book book){
        book.setAvailable(false);
        return bookRepository.save(book);
    }
    
}
