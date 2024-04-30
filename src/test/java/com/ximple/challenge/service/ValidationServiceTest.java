/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ximple.challenge.service;

import java.util.Optional;
import com.ximple.challenge.entity.Book;
import com.ximple.challenge.entity.UserAccount;
import com.ximple.challenge.repository.BookRepository;
import com.ximple.challenge.repository.UserAccountRepository;

import com.ximple.challenge.exception.UserNotFoundException;
import com.ximple.challenge.exception.BookNotFoundException;
import com.ximple.challenge.exception.BookReservationException;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 * @author ignis
 */
@ExtendWith(MockitoExtension.class)
public class ValidationServiceTest {

    private Book bookOne;
    private Book bookTwo;

    private UserAccount userAccountOne;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserAccountRepository userRepository;

    @InjectMocks
    private ValidationService validationService;

    @BeforeEach
    public void init() {

        bookOne = new Book();
        bookOne.setId(1L);
        bookOne.setTitle("Best Book in the World");
        bookOne.setAuthor("Omar Alvarez");
        bookOne.setAvailable(true);

        bookTwo = new Book();
        bookTwo.setId(2L);
        bookTwo.setTitle("Worst Book in the World");
        bookTwo.setAuthor("Anonymous Author");

    }

    @Test
    public void testvalidateBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookOne));

        var book = validationService.validateBookById(1L);

        assertEquals(1L, book.getId());
        assertEquals("Best Book in the World", book.getTitle());
        assertEquals("Omar Alvarez", book.getAuthor());
        assertEquals(Boolean.TRUE, book.isAvailable());
    }

    @Test
    public void testValidateBookByIdWithException() {

        when(bookRepository.findById(1L)).thenThrow(new BookNotFoundException());

        Exception exception = assertThrows(BookNotFoundException.class, () -> {

            validationService.validateBookById(1L);
        });

        String actualMessage = exception.getMessage();
        assertEquals(true, actualMessage.contains("Book not found"));

    }

    @Test
    public void testValidateBookReservationById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookOne));

        var book = validationService.validateBookReservationById(1L);

        assertEquals(1L, book.getId());
        assertEquals("Best Book in the World", book.getTitle());
        assertEquals("Omar Alvarez", book.getAuthor());
        assertEquals(Boolean.TRUE, book.isAvailable());
    }

    @Test
    public void testValidateBookReservationByIdWithException() {
        when(bookRepository.findById(2L)).thenReturn(Optional.of(bookTwo));

        Exception exception = assertThrows(BookReservationException.class, () -> {

            validationService.validateBookReservationById(2L);
        });

        String actualMessage = exception.getMessage();
        assertEquals(true, actualMessage.contains("The book Worst Book in the World is not available"));
    }

    @Test
    public void testValidateUserById() {

        userAccountOne = new UserAccount("usernameOne", "Omar", "alvarez");
        userAccountOne.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(userAccountOne));

        var user = validationService.validateUserById(1L);

        assertEquals(1L, user.getId());
        assertEquals("Omar", user.getName());
        assertEquals("alvarez", user.getLastName());
        assertEquals("usernameOne", user.getUsername());
    }

    @Test
    public void testValidateUserByIdWithException() {

        when(userRepository.findById(1L)).thenThrow(new UserNotFoundException());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {

            validationService.validateUserById(1L);
        });

        String actualMessage = exception.getMessage();
        assertEquals(true, actualMessage.contains("User Not found"));
    }
}
