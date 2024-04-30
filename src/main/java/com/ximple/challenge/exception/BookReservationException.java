/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ximple.challenge.exception;

import java.text.MessageFormat;

/**
 *
 * @author ignis
 */
public class BookReservationException extends RuntimeException {

    public BookReservationException(String title) {
        super(MessageFormat.format("The book {0} is not available", title));
    }
    
}
