package com.eduardobastos.dinningreviews.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.eduardobastos.dinningreviews.models.DinningReview;
import com.eduardobastos.dinningreviews.models.DinningReviewStatus;
import com.eduardobastos.dinningreviews.repositories.DinningReviewRepository;
import com.eduardobastos.dinningreviews.repositories.RestaurantRepository;
import com.eduardobastos.dinningreviews.repositories.UserRepository;

@RestController
@RequestMapping("/admin")
public class ReviewApprovalController {

    private DinningReviewRepository drr;
    private UserRepository ur;
    private RestaurantRepository rr;

    public ReviewApprovalController(DinningReviewRepository dinningReviewRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.drr = dinningReviewRepository;
        this.ur = userRepository;
        this.rr = restaurantRepository;
    }

    @GetMapping("")
    public List<DinningReview> getAllPendingApproval() {
        List<DinningReview> createdReviews = drr.findAllByStatus(DinningReviewStatus.CREATED);
        return createdReviews;
    }

    @PostMapping("/{reviewId}/approve")
    public DinningReview approveReview(@PathVariable Integer reviewId) {
        Optional<DinningReview> reviewToUpdateOptional = drr.findById(reviewId);
        if (!reviewToUpdateOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review does not exist");
        }
        DinningReview reviewToUpdate = reviewToUpdateOptional.get();
        reviewToUpdate.setStatus(DinningReviewStatus.APPROVED);
        DinningReview updatedReview = drr.save(reviewToUpdate);
        return updatedReview;
    }

    @PostMapping("/{reviewId}/deny")
    public DinningReview denyReview(@PathVariable Integer reviewId) {
        Optional<DinningReview> reviewToUpdateOptional = drr.findById(reviewId);
        if (!reviewToUpdateOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review does not exist");
        }
        DinningReview reviewToUpdate = reviewToUpdateOptional.get();
        reviewToUpdate.setStatus(DinningReviewStatus.DENIED);
        DinningReview updatedReview = drr.save(reviewToUpdate);
        return updatedReview;
    }

    // TODO: Remove this for final commit
    // COMMENT: Created this endpoint to easily clean up database
    @DeleteMapping("/purge")
    public void purgeData() {
        drr.deleteAll();
        rr.deleteAll();
        ur.deleteAll();
    }
}
