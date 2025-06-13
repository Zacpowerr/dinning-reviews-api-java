package com.eduardobastos.dinningreviews.controllers;

import java.util.ArrayList;
import java.util.Comparator;
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
import com.eduardobastos.dinningreviews.models.DinningReviewStatus;
import com.eduardobastos.dinningreviews.models.Restaurant;
import com.eduardobastos.dinningreviews.repositories.DinningReviewRepository;
import com.eduardobastos.dinningreviews.repositories.RestaurantRepository;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private RestaurantRepository rr;
    private DinningReviewRepository drr;

    public RestaurantController(RestaurantRepository restaurantRepository, DinningReviewRepository dinningReviewRepository) {
        this.rr = restaurantRepository;
        this.drr = dinningReviewRepository;
    }

    @GetMapping("")
    public Iterable<Restaurant> getAllRestaurants() {
        return rr.findAll();
    }

    @PostMapping("")
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
        Integer existingRestaurants = rr.countByNameAndZipCode(restaurant.getName(), restaurant.getZipCode());
        if (existingRestaurants > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Restaurant already exists");
        }
        return rr.save(restaurant);
    }

    @PutMapping("/{restaurantId}")
    public Restaurant updateRestaurant(@PathVariable Integer restaurantId, @RequestBody Restaurant restaurant) {
        Optional<Restaurant> restaurantToUpdateOptional = rr.findById(restaurantId);

        if (!restaurantToUpdateOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Restaurant does not exist");
        }
        Restaurant restaurantToUpdate = restaurantToUpdateOptional.get();
        restaurantToUpdate.setName(restaurant.getName());
        restaurantToUpdate.setZipCode(restaurant.getZipCode());
        Restaurant updatedRestaurant = rr.save(restaurantToUpdate);
        return updatedRestaurant;
    }

    @DeleteMapping("/{restaurantId}")
    public Restaurant deleteRestaurant(@PathVariable Integer restaurantId) {
        Optional<Restaurant> restaurantToDeleteOptional = rr.findById(restaurantId);
        if (!restaurantToDeleteOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant does not exist");
        }
        Restaurant restaurantToDelete = restaurantToDeleteOptional.get();
        rr.delete(restaurantToDelete);
        return restaurantToDelete;
    }

    @GetMapping("/search")
    public List<Restaurant> searchRestaurant(@RequestParam String zipCode, @RequestParam String allergyType) {

        if (!zipCode.isEmpty() && !allergyType.isEmpty()) {
            List<Restaurant> restaurants
                    = rr.findAllByZipCode(zipCode);
            switch (allergyType) {
                case "egg":
                    restaurants.sort(Comparator.comparingDouble((Restaurant r) -> r.getEggScore()).reversed());
                    return restaurants;
                case "diary":
                    restaurants.sort(Comparator.comparingDouble((Restaurant r) -> r.getDairyScore()).reversed());
                case "peanut":
                    restaurants.sort(Comparator.comparingDouble((Restaurant r) -> r.getPeanutScore()).reversed());
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Allergen is not tracked");
            }

        }
        if (!zipCode.isEmpty() && allergyType.isEmpty()) {
            return rr.findAllByZipCode(zipCode);
        }
        return new ArrayList<Restaurant>();
    }

    @GetMapping("/{restaurantId}/reviews")
    public List<DinningReview> getRestaurantReviews(@PathVariable Integer restaurantId) {
        Optional<Restaurant> existingRestaurantOptional = rr.findById(restaurantId);
        if (!existingRestaurantOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant does not exist");
        }
        return drr.findAllByRestaurantIdAndStatus(restaurantId, DinningReviewStatus.APPROVED);
    }

}
