package com.eduardobastos.dinningreviews;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.eduardobastos.dinningreviews.controllers.RestaurantController;
import com.eduardobastos.dinningreviews.models.DinningReview;
import com.eduardobastos.dinningreviews.models.Restaurant;
import com.eduardobastos.dinningreviews.repositories.DinningReviewRepository;
import com.eduardobastos.dinningreviews.repositories.RestaurantRepository;

class RestaurantControllerTest {

    private RestaurantRepository restaurantRepository;
    private DinningReviewRepository dinningReviewRepository;
    private RestaurantController controller;

    @BeforeEach
    void setUp() {
        restaurantRepository = mock(RestaurantRepository.class);
        dinningReviewRepository = mock(DinningReviewRepository.class);
        controller = new RestaurantController(restaurantRepository, dinningReviewRepository);
    }

    @Test
    void getAllRestaurants_returnsAllRestaurants() {
        List<Restaurant> restaurants = Arrays.asList(new Restaurant(), new Restaurant());
        when(restaurantRepository.findAll()).thenReturn(restaurants);

        Iterable<Restaurant> result = controller.getAllRestaurants();

        assertEquals(restaurants, result);
    }

    @Test
    void addRestaurant_savesAndReturnsRestaurant_whenNotExists() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test");
        restaurant.setZipCode("12345");
        when(restaurantRepository.countByNameAndZipCode("Test", "12345")).thenReturn(0);
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

        Restaurant result = controller.addRestaurant(restaurant);

        assertEquals(restaurant, result);
        verify(restaurantRepository).save(restaurant);
    }

    @Test
    void addRestaurant_throwsConflict_whenRestaurantExists() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test");
        restaurant.setZipCode("12345");
        when(restaurantRepository.countByNameAndZipCode("Test", "12345")).thenReturn(1);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> controller.addRestaurant(restaurant));
        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
    }

    @Test
    void updateRestaurant_updatesAndReturnsRestaurant_whenExists() {
        Restaurant existing = new Restaurant();
        existing.setId(1);
        Restaurant update = new Restaurant();
        update.setName("Updated");
        update.setZipCode("54321");
        update.setDairyScore(5.0);
        update.setEggScore(4.0);
        update.setPeanutScore(3.0);

        when(restaurantRepository.findById(1)).thenReturn(Optional.of(existing));
        when(restaurantRepository.save(existing)).thenReturn(existing);

        Restaurant result = controller.updateRestaurant(1, update);

        assertEquals(existing, result);
        assertEquals("Updated", existing.getName());
        assertEquals("54321", existing.getZipCode());
        assertEquals(5, existing.getDairyScore());
        assertEquals(4, existing.getEggScore());
        assertEquals(3, existing.getPeanutScore());
    }

    @Test
    void updateRestaurant_throwsNotFound_whenRestaurantDoesNotExist() {
        when(restaurantRepository.findById(1)).thenReturn(Optional.empty());
        Restaurant update = new Restaurant();

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> controller.updateRestaurant(1, update));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void deleteRestaurant_deletesAndReturnsRestaurant_whenExists() {
        Restaurant existing = new Restaurant();
        existing.setId(1);
        when(restaurantRepository.findById(1)).thenReturn(Optional.of(existing));

        Restaurant result = controller.deleteRestaurant(1);

        assertEquals(existing, result);
        verify(restaurantRepository).delete(existing);
    }

    @Test
    void deleteRestaurant_throwsNotFound_whenRestaurantDoesNotExist() {
        when(restaurantRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> controller.deleteRestaurant(1));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void searchRestaurant_returnsRestaurantsByZipCode() {
        List<Restaurant> restaurants = Arrays.asList(new Restaurant());
        when(restaurantRepository.findAllByZipCode("12345")).thenReturn(restaurants);

        List<Restaurant> result = controller.searchRestaurant("12345", "");

        assertEquals(restaurants, result);
    }

    @Test
    void searchRestaurant_returnsEmptyList_whenZipCodeEmpty() {
        List<Restaurant> result = controller.searchRestaurant("", "");
        assertTrue(result.isEmpty());
    }

    @Test
    void getRestaurantReviews_returnsReviews_whenRestaurantExists() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        List<DinningReview> reviews = Arrays.asList(new DinningReview());
        when(restaurantRepository.findById(1)).thenReturn(Optional.of(restaurant));
        when(dinningReviewRepository.findAllByRestaurantId(1)).thenReturn(reviews);

        List<DinningReview> result = controller.getRestaurantReviews(1);

        assertEquals(reviews, result);
    }

    @Test
    void getRestaurantReviews_throwsNotFound_whenRestaurantDoesNotExist() {
        when(restaurantRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> controller.getRestaurantReviews(1));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
}
