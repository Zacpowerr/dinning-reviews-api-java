package com.eduardobastos.dinningreviews.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
