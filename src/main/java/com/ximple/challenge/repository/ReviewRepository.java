/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ximple.challenge.repository;

import com.ximple.challenge.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ignis
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    public Page<Review> findAll(Pageable pageable);
    
    public Page<Review> findByBookId(Long bookId, Pageable pageable);

}
