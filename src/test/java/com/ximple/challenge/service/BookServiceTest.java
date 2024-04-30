/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ximple.challenge.service;

import java.util.List;
import com.ximple.challenge.entity.Book;
import com.ximple.challenge.repository.BookRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 *
 * @author ignis
 */
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    
    private Book bookOne;
    private Book bookTwo;
    
    @Mock
    private BookRepository bookRepository;
    
    @InjectMocks
    private BookService bookService;
    
    @BeforeEach
    public void init() {

        bookOne = new Book();
        bookOne.setId(1L);
        bookOne.setTitle("Best Book in the World");
        bookOne.setAuthor("Omar Alvarez");
        bookOne.setAvailable(true);

    }
    
    @Test
    public void testGetAvailableBooks() {
        
        Page<Book> booksPage = new PageImpl(List.of(bookOne));
        when(bookRepository.findByAvailableTrue(PageRequest.of(0, 10))).thenReturn(booksPage);
        
        var books = bookService.getAvailableBooks(PageRequest.of(0, 10)).getContent();
        
        assertEquals(1, books.size());
        
        var book = books.get(0);
        
        assertEquals(1L, book.getId());
        assertEquals("Omar Alvarez", book.getAuthor());
        assertEquals(Boolean.TRUE, book.isAvailable());
        assertEquals("Best Book in the World", book.getTitle());
    }
    
    @Test
    public void testMarkAsUnavailable() {
        bookOne.setAvailable(false);
        when(bookRepository.save(bookOne)).thenReturn(bookOne);
        
        var book = bookService.markAsUnavailable(bookOne);
        
        assertEquals(1L, book.getId());
        assertEquals("Omar Alvarez", book.getAuthor());
        assertEquals(Boolean.FALSE, book.isAvailable());
        assertEquals("Best Book in the World", book.getTitle());
        
    }
}
