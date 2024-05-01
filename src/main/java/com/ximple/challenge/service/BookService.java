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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ignis
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    Logger logger = LoggerFactory.getLogger(BookService.class);
    
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    @Cacheable(cacheNames = "availablebooks")
    public Page<Book> getAvailableBooks(Pageable pageable) {
        logger.info("retrieving available books from database");
        return bookRepository.findByAvailableTrue(pageable);
    }

    @CacheEvict(value="availablebooks", allEntries=true)
    public Book markAsUnavailable(Book book) {
        
        logger.info("marking book as not availability, cleaning cache");
        book.setAvailable(false);
        return bookRepository.save(book);
    }

}
