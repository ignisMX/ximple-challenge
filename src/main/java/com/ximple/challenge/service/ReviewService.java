/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ximple.challenge.service;

import com.ximple.challenge.entity.Review;
import com.ximple.challenge.record.ReviewRequest;
import com.ximple.challenge.repository.ReviewRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

/**
 *
 * @author ignis
 */
@Service
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    
    private final ValidationService validationService;
    
    Logger logger = LoggerFactory.getLogger(ReviewService.class);
    
    public ReviewService(ReviewRepository reviewRepository, ValidationService validationService) {
        this.reviewRepository = reviewRepository;
        this.validationService = validationService;
    }
    
    @CacheEvict(value="bookreviews", allEntries=true)
    public Review createReview(Long bookId, Long userId, ReviewRequest reviewRequest){
        var userAccount = validationService.validateUserById(userId);
        var book = validationService.validateBookById(bookId);
        var review = new Review(reviewRequest.review(), book, userAccount);
        
        return reviewRepository.save(review);
    }
    
    public Page<Review> getReviews(Pageable pageable){
        
        return reviewRepository.findAll(pageable);
    }
    
    @Cacheable(cacheNames = "bookreviews")
    public Page<Review> getReviewsByBookId(Long bookId, Pageable pageable){
        
        logger.info("retrieving reviews with book id {} from database", bookId);
        return reviewRepository.findByBookId(bookId, pageable);
    }
}
