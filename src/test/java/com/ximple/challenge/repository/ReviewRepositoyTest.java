/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ximple.challenge.repository;

import com.ximple.challenge.entity.Book;
import com.ximple.challenge.entity.Review;
import com.ximple.challenge.entity.UserAccount;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

/**
 *
 * @author ignis
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewRepositoyTest {

    private Book bookOne;
    private Book bookTwo;

    private UserAccount userAccountOne;
    private UserAccount userAccountTwo;

    private Review reviewOne;
    private Review reviewTwo;
    private Review reviewThree;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @BeforeEach
    public void init() {

        bookOne = new Book();
        bookOne.setTitle("Best Book in the World");
        bookOne.setAuthor("Omar Alvarez");
        bookOne.setAvailable(true);
        bookOne = bookRepository.save(bookOne);

        bookTwo = new Book();
        bookTwo.setTitle("Worst Book in the World");
        bookTwo.setAuthor("Anonymous Author");
        bookTwo = bookRepository.save(bookTwo);

        userAccountOne = new UserAccount("usernameOne", "Omar", "alvarez");
        userAccountOne = userAccountRepository.save(userAccountOne);
        
        userAccountTwo = new UserAccount("usernameTwo", "Jhony", "Deep");
        userAccountTwo = userAccountRepository.save(userAccountTwo);
        
        reviewOne = new Review("Great book", bookOne, userAccountOne);
        reviewOne = reviewRepository.save(reviewOne);
        
        reviewTwo = new Review("Not Baad", bookOne, userAccountTwo);
        reviewTwo = reviewRepository.save(reviewTwo);
        
        reviewThree = new Review("Bad book", bookTwo, userAccountOne);
        reviewThree = reviewRepository.save(reviewThree);
    }

    @Test
    public void testFindAll() {
        
        var reviews = reviewRepository.findAll(PageRequest.of(0, 10)).getContent();
        
        assertEquals(3, reviews.size());
    }
    
    @Test
    public void testFindByBookId() {
        
        var reviews = reviewRepository.findByBookId(bookOne.getId(), PageRequest.of(0, 10))
                .getContent();
        
        assertEquals(2, reviews.size());
    }
}
