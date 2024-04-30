/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ximple.challenge.service;

import java.util.List;
import com.ximple.challenge.entity.Book;
import com.ximple.challenge.entity.Review;
import com.ximple.challenge.entity.UserAccount;
import com.ximple.challenge.record.ReviewRequest;
import com.ximple.challenge.repository.ReviewRepository;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 *
 * @author ignis
 */
@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    private Book bookOne;

    private Review reviewOne;
    private Review reviewTwo;

    private UserAccount userAccountOne;
    private UserAccount userAccountTwo;

    private ReviewRequest reviewRequestOne;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    public void init() {

        bookOne = new Book();
        bookOne.setId(1L);
        bookOne.setTitle("Best Book in the World");
        bookOne.setAuthor("Omar Alvarez");
        bookOne.setAvailable(true);

        userAccountOne = new UserAccount("usernameOne", "Omar", "alvarez");
        userAccountOne.setId(1L);
        
        userAccountTwo = new UserAccount("usernameTwo", "Jhony", "Deep");
        userAccountTwo.setId(2L);

        reviewOne = new Review("Great Book", bookOne, userAccountOne);
        reviewOne.setId(1L);
        
        reviewTwo = new Review("Awesome Book", bookOne, userAccountTwo);
        reviewTwo.setId(2L);

        reviewRequestOne = new ReviewRequest("Great Book");
    }

    @Test
    public void testCreateReview() {
        
        when(validationService.validateUserById(1L)).thenReturn(userAccountOne);
        when(validationService.validateBookById(1L)).thenReturn(bookOne);
        when(reviewRepository.save(any())).thenReturn(reviewOne);

        var review = reviewService.createReview(1L, 1L, reviewRequestOne);

        assertEquals(1L, review.getId());
        assertEquals("Great Book", review.getDescription());

        assertEquals(1L, review.getBook().getId());
        assertEquals("Omar Alvarez", review.getBook().getAuthor());
        assertEquals("Best Book in the World", review.getBook().getTitle());
        assertEquals(Boolean.TRUE, review.getBook().isAvailable());

        assertEquals(1L, review.getUserAccount().getId());
        assertEquals("Omar", review.getUserAccount().getName());
        assertEquals("alvarez", review.getUserAccount().getLastName());
        assertEquals("usernameOne", review.getUserAccount().getUsername());

    }
    
    @Test
    public void testGetReviews() {
        
        Page<Review> reviewPage = new PageImpl(List.of(reviewOne, reviewTwo));
        
        when(reviewRepository.findAll(PageRequest.of(0, 10))).thenReturn(reviewPage);
        
        var reviews = reviewService.getReviews(PageRequest.of(0, 10)).getContent();
        
        assertEquals(2, reviews.size());
    }
    
    @Test
    public void testGetReviewsByBookId() {
        
        Page<Review> reviewPage = new PageImpl(List.of(reviewOne, reviewTwo));
        
        when(reviewRepository.findByBookId(1L,PageRequest.of(0, 10))).thenReturn(reviewPage);
        
        var reviews = reviewService.getReviewsByBookId(1L, PageRequest.of(0, 10)).getContent();
        
        assertEquals(2, reviews.size());
    }
}
