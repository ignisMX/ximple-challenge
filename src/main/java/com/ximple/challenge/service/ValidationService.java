/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ximple.challenge.service;

import com.ximple.challenge.entity.Book;
import com.ximple.challenge.entity.UserAccount;
import com.ximple.challenge.repository.BookRepository;
import com.ximple.challenge.repository.UserAccountRepository;

import com.ximple.challenge.exception.UserNotFoundException;
import com.ximple.challenge.exception.BookNotFoundException;
import com.ximple.challenge.exception.BookReservationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

/**
 *
 * @author ignis
 */
@Service
public class ValidationService {
    private final BookRepository bookRepository;
    private final UserAccountRepository userRepository;
    
    Logger logger = LoggerFactory.getLogger(ValidationService.class);

    public ValidationService(BookRepository bookRepository, UserAccountRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }
    
    @Cacheable(cacheNames = "books", key = "#bookId")
    public Book validateBookById(Long bookId){
        logger.info("retrieving book with id {} from database", bookId);
        var book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException());
        
        return book;
    }
    
    public Book validateBookReservationById(Long bookId){
        
        var book = validateBookById(bookId);
        
        if(!book.isAvailable()){
            throw new BookReservationException(book.getTitle());
        }
        
        return book;
    }
    
    @Cacheable(cacheNames = "useraccounts", key = "#userId")
    public UserAccount validateUserById(Long userId){
        
        logger.info("retrieving user account with id {} from database", userId);
        var userAccount = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        
        return userAccount;
    
    }
}
