package com.eduardobastos.dinningreviews.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.eduardobastos.dinningreviews.models.DinningReview;
import com.eduardobastos.dinningreviews.models.DinningReviewStatus;

public interface DinningReviewRepository extends CrudRepository<DinningReview, Integer> {

    public List<DinningReview> findAllByStatus(DinningReviewStatus status);

    public List<DinningReview> findAllByRestaurantIdAndStatus(Integer restaurantId, DinningReviewStatus status);

    public List<DinningReview> findAllByRestaurantId(Integer restaurantId);

    public List<DinningReview> findAllByUserName(String username);
}
