/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ximple.challenge.service;

import com.ximple.challenge.entity.Reservation;
import com.ximple.challenge.repository.ReservationRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author ignis
 */

@Service
public class ReservationService {
    
    private final BookService bookService;
    
    private final ValidationService validationService;
    
    private final ReservationRepository reservationRepository;

    public ReservationService(BookService bookService, ValidationService validationService, ReservationRepository reservationRepository) {
        this.bookService = bookService;
        this.validationService = validationService;
        this.reservationRepository = reservationRepository;
    }

    public Reservation createReservation(Long bookId, Long userId){
        var userAccount = validationService.validateUserById(userId);
        var book = validationService.validateBookReservationById(bookId);
        bookService.markAsUnavailable(book);
        var reservation = new  Reservation(userAccount, book);
        reservation = reservationRepository.save(reservation);
        return reservation;
    }

}
