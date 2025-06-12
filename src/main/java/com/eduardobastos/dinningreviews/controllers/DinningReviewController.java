package com.eduardobastos.dinningreviews.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.eduardobastos.dinningreviews.models.DinningReview;
import com.eduardobastos.dinningreviews.models.User;
import com.eduardobastos.dinningreviews.repositories.DinningReviewRepository;
import com.eduardobastos.dinningreviews.repositories.UserRepository;

@RestController
@RequestMapping("/reviews")
public class DinningReviewController {

    private DinningReviewRepository drr;
    private UserRepository ur;

    public DinningReviewController(DinningReviewRepository dinningReviewRepository, UserRepository userRepository) {
        this.drr = dinningReviewRepository;
        this.ur = userRepository;
    }

    @GetMapping("")
    public Iterable<DinningReview> getAllReviews() {
        return drr.findAll();
    }

    @PostMapping("")
    public DinningReview addReview(@RequestBody DinningReview review) {
        Optional<User> existingUser = ur.findByUserName(review.getUserName());
        if (!existingUser.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User does not exist"
            );
        }
        return drr.save(review);
    }

    @PutMapping("/{reviewId}")
    public DinningReview addReview(@PathVariable Integer reviewId, @RequestBody DinningReview review) {
        Optional<DinningReview> reviewToUpdateOptional = drr.findById(reviewId);
        if (!reviewToUpdateOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review does not exist.");
        }
        Optional<User> existingUser = ur.findByUserName(review.getUserName());
        if (!existingUser.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User does not exist"
            );
        }
        return drr.save(review);
    }

    @DeleteMapping("/{reviewId}")
    public DinningReview deleteReview(@PathVariable Integer reviewId) {
        Optional<DinningReview> reviewToDeleteOptional = drr.findById(reviewId);
        if (!reviewToDeleteOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review does not exist.");
        }
        DinningReview reviewToDelete = reviewToDeleteOptional.get();
        drr.delete(reviewToDelete);
        return reviewToDelete;
    }

    @GetMapping("/search")
    public List<DinningReview> searchReviews(@RequestParam Integer restaurantId) {
        if (restaurantId != null) {
            return drr.findAllByRestaurantId(restaurantId);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review does not exist");
    }

}
