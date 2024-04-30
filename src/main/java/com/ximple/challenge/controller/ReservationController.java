/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ximple.challenge.controller;

import com.ximple.challenge.entity.Reservation;
import com.ximple.challenge.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ignis
 */
@RestController
@RequestMapping("/reservations")
public class ReservationController {
    
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    
    @PostMapping("/books/{bookId}/users/{userId}")
    public ResponseEntity<Reservation> createReservation(@PathVariable Long bookId, @PathVariable Long userId){
        
        var reservation = reservationService.createReservation(bookId, bookId);
        
        return ResponseEntity.ok(reservation);
    }
}
