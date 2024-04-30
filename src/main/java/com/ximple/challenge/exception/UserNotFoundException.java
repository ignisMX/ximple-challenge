/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ximple.challenge.exception;

/**
 *
 * @author ignis
 */
public class UserNotFoundException extends RuntimeException {


    public UserNotFoundException() {
        super("User Not found");
    }
    
}
