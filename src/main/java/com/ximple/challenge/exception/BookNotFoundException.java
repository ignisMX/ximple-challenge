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
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException() {
        super("Book not found");
    }
    
    

    public BookNotFoundException(String title) {
        super(MessageFormat.format("The book {0} doesn't exist", title));
    }
    
}
