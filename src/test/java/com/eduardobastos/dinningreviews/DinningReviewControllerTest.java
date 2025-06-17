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

import com.eduardobastos.dinningreviews.controllers.DinningReviewController;
import com.eduardobastos.dinningreviews.models.DinningReview;
import com.eduardobastos.dinningreviews.models.Restaurant;
import com.eduardobastos.dinningreviews.models.User;
import com.eduardobastos.dinningreviews.repositories.DinningReviewRepository;
import com.eduardobastos.dinningreviews.repositories.RestaurantRepository;
import com.eduardobastos.dinningreviews.repositories.UserRepository;

class DinningReviewControllerTest {

    private DinningReviewRepository drr;
    private UserRepository ur;
    private RestaurantRepository rr;
    private DinningReviewController controller;

    @BeforeEach
    void setUp() {
        drr = mock(DinningReviewRepository.class);
        ur = mock(UserRepository.class);
        rr = mock(RestaurantRepository.class);
        controller = new DinningReviewController(drr, ur, rr);
    }

    @Test
    void getAllReviews_returnsAllReviews() {
        List<DinningReview> reviews = Arrays.asList(new DinningReview(), new DinningReview());
        when(drr.findAll()).thenReturn(reviews);

        Iterable<DinningReview> result = controller.getAllReviews();

        assertEquals(reviews, result);
    }

    @Test
    void addReview_savesAndReturnsReview_whenUserExists() {
        DinningReview review = new DinningReview();
        Restaurant restaurantToCreate = new Restaurant();
        restaurantToCreate.setName("Pizza place");
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1); // set a mock id
        restaurant.setName("Pizza place");
        when(rr.save(restaurantToCreate)).thenReturn(restaurant);
        review.setUserName("user1");
        review.setRestaurantId(restaurant.getId());
        User user = new User();
        when(ur.findByUserName("user1")).thenReturn(Optional.of(user));
        when(drr.save(review)).thenReturn(review);
        when(rr.findById(restaurant.getId())).thenReturn(Optional.of(restaurant));

        DinningReview result = controller.addReview(review);

        assertEquals(review, result);
        verify(drr).save(review);
    }

    @Test
    void addReview_throwsNotFound_whenUserDoesNotExist() {
        DinningReview review = new DinningReview();
        review.setUserName("user1");
        when(ur.findByUserName("user1")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> controller.addReview(review));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("User does not exist"));
    }

    @Test
    void updateReview_updatesAndReturnsReview_whenReviewAndUserExist() {
        DinningReview review = new DinningReview();
        review.setUserName("user1");
        review.setRestaurantId(1); // Make sure restaurantId is set
        User user = new User();
        DinningReview existing = new DinningReview();
        when(drr.findById(1)).thenReturn(Optional.of(existing));
        when(ur.findByUserName("user1")).thenReturn(Optional.of(user));
        when(drr.save(review)).thenReturn(review);

        // Add this line to mock restaurant lookup
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        when(rr.findById(1)).thenReturn(Optional.of(restaurant));

        DinningReview result = controller.addReview(1, review);

        assertEquals(review, result);
        verify(drr).save(review);
    }

    @Test
    void updateReview_throwsNotFound_whenReviewDoesNotExist() {
        DinningReview review = new DinningReview();
        when(drr.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> controller.addReview(1, review));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Review does not exist"));
    }

    @Test
    void updateReview_throwsNotFound_whenUserDoesNotExist() {
        DinningReview review = new DinningReview();
        review.setUserName("user1");
        when(drr.findById(1)).thenReturn(Optional.of(new DinningReview()));
        when(ur.findByUserName("user1")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> controller.addReview(1, review));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("User does not exist"));
    }

    @Test
    void deleteReview_deletesAndReturnsReview_whenExists() {
        DinningReview review = new DinningReview();
        when(drr.findById(1)).thenReturn(Optional.of(review));

        DinningReview result = controller.deleteReview(1);

        assertEquals(review, result);
        verify(drr).delete(review);
    }

    @Test
    void deleteReview_throwsNotFound_whenReviewDoesNotExist() {
        when(drr.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> controller.deleteReview(1));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Review does not exist"));
    }

    @Test
    void searchReviews_returnsReviewsByRestaurantId() {
        List<DinningReview> reviews = Arrays.asList(new DinningReview());
        when(drr.findAllByRestaurantId(1)).thenReturn(reviews);

        List<DinningReview> result = controller.searchReviews(1);

        assertEquals(reviews, result);
    }

    @Test
    void searchReviews_throwsNotFound_whenRestaurantIdIsNull() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> controller.searchReviews(null));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Review does not exist"));
    }
}
