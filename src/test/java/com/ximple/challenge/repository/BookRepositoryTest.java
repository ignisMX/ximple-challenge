/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ximple.challenge.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import org.junit.jupiter.api.Test;
import com.ximple.challenge.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ignis
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    
    private Book bookOne;
    private Book bookTwo;
    
    @Autowired
    private BookRepository bookRepository;

    
    @BeforeEach
    public void init(){
        
        bookOne = new Book();
        bookOne.setTitle("Best Book in the World");
        bookOne.setAuthor("Omar Alvarez");
        bookOne.setAvailable(true);
        bookOne = bookRepository.save(bookOne);
        
        bookTwo = new Book();
        bookTwo.setTitle("Worst Book in the World");
        bookTwo.setAuthor("Anonymous Author");
        bookTwo = bookRepository.save(bookTwo);
        
    }
    
    @Test
    public void testFindByAvailableTrue() {
        var books = bookRepository.findByAvailableTrue( PageRequest.of(0, 10)).getContent();
        
        assertEquals(1, books.size());
        var book = books.get(0);
        assertEquals("Best Book in the World", book.getTitle());
        assertEquals("Omar Alvarez", book.getAuthor());
        assertEquals(Boolean.TRUE, book.isAvailable());
        
    }
    
}
