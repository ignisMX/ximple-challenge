/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ximple.challenge.service;

import com.ximple.challenge.entity.Book;
import com.ximple.challenge.entity.Reservation;
import com.ximple.challenge.entity.UserAccount;
import com.ximple.challenge.repository.ReservationRepository;


import org.mockito.Mock;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
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
public class ReservationServiceTest {
    
    private Book bookOne;
    private Book bookTwo;

    private UserAccount userAccountOne;
    
    private Reservation reservationOne;

    @Mock
    private BookService bookService;

    @Mock
    private ValidationService validationService;

    @Mock
    private ReservationRepository reservationRepository;
    
    @InjectMocks
    private ReservationService reservationService;
    
    @BeforeEach
    public void init() {

        bookOne = new Book();
        bookOne.setId(1L);
        bookOne.setTitle("Best Book in the World");
        bookOne.setAuthor("Omar Alvarez");
        bookOne.setAvailable(true);
        
        bookTwo = new Book();
        bookTwo.setId(1L);
        bookTwo.setTitle("Best Book in the World");
        bookTwo.setAuthor("Omar Alvarez");
        bookTwo.setAvailable(false);

        userAccountOne = new UserAccount("usernameOne", "Omar", "alvarez");
        userAccountOne.setId(1L);
        
        reservationOne = new Reservation(userAccountOne, bookTwo);
        reservationOne.setId(1L);
    }

    
    @Test
    public void testCreateReservation() {
        
        when(validationService.validateUserById(1L)).thenReturn(userAccountOne);
        when(validationService.validateBookReservationById(1L)).thenReturn(bookOne);
        when(bookService.markAsUnavailable(bookOne)).thenReturn(bookTwo);
        when(reservationRepository.save(any())).thenReturn(reservationOne);
        
        var reservation = reservationService.createReservation(1L, 1L);
        
        assertEquals(1L, reservation.getId());
        assertEquals(1L, reservation.getBook().getId());
        assertEquals("Omar Alvarez", reservation.getBook().getAuthor());
        assertEquals("Best Book in the World", reservation.getBook().getTitle());
        assertEquals(Boolean.FALSE, reservation.getBook().isAvailable());
        
        assertEquals(1L, reservation.getUserAccount().getId());
        assertEquals("Omar", reservation.getUserAccount().getName());
        assertEquals("alvarez", reservation.getUserAccount().getLastName());
        assertEquals("usernameOne", reservation.getUserAccount().getUsername());
        
    }
}
