/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ximple.challenge.controller;

import com.ximple.challenge.entity.Review;
import com.ximple.challenge.record.ReviewRequest;
import com.ximple.challenge.service.ReviewService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ignis
 */
@RestController
@RequestMapping("/reviews")
public class ReviewController {
    
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    
    @PostMapping("/books/{bookId}/users/{userId}")
    public ResponseEntity<Review> createReview(@PathVariable Long bookId, @PathVariable Long userId, @RequestBody ReviewRequest reviewRequest){
    
        var review = reviewService.createReview(bookId, userId, reviewRequest);
        
        return ResponseEntity.ok(review);
    }
    
    @RequestMapping
    public Page<Review> getReviewsAsPage(Pageable pageable){
        
        return reviewService.getReviews(pageable);
    }
    
    @RequestMapping("/books/{bookId}")
    public Page<Review> getReviewsByBookId(@PathVariable Long bookId, Pageable pageable){
    
        return reviewService.getReviewsByBookId(bookId, pageable);
    }
}
