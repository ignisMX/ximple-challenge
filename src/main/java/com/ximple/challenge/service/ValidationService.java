/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ximple.challenge.service;

import com.ximple.challenge.entity.Book;
import com.ximple.challenge.entity.UserAccount;
import com.ximple.challenge.exception.BookNotFoundException;
import com.ximple.challenge.exception.BookReservationException;
import com.ximple.challenge.exception.UserNotFoundException;
import com.ximple.challenge.repository.BookRepository;
import org.springframework.stereotype.Service;
import com.ximple.challenge.repository.UserAccountRepository;

/**
 *
 * @author ignis
 */
@Service
public class ValidationService {
    private final BookRepository bookRepository;
    private final UserAccountRepository userRepository;

    public ValidationService(BookRepository bookRepository, UserAccountRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

   
    public Book validateBookByTitle(String title){
    
        var book = bookRepository.findByTitle(title).orElseThrow(()-> new BookNotFoundException(title));
        
        return book;
    }
    
    public Book validateBookById(Long bookId){
        
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
    
    
    public UserAccount validateUserById(Long userId){
        
        var userAccount = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        
        return userAccount;
    
    }
}
