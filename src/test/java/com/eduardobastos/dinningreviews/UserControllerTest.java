package com.eduardobastos.dinningreviews;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.eduardobastos.dinningreviews.controllers.UserController;
import com.eduardobastos.dinningreviews.models.DinningReview;
import com.eduardobastos.dinningreviews.models.User;
import com.eduardobastos.dinningreviews.repositories.DinningReviewRepository;
import com.eduardobastos.dinningreviews.repositories.UserRepository;

class UserControllerTest {

    private UserRepository userRepository;
    private DinningReviewRepository dinningReviewRepository;
    private UserController controller;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        dinningReviewRepository = mock(DinningReviewRepository.class);
        controller = new UserController(userRepository, dinningReviewRepository);
    }

    @Test
    void getUsers_returnsAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        Iterable<User> result = controller.getUsers();

        assertEquals(users, result);
    }

    @Test
    void addUser_savesAndReturnsUser_whenNotExists() {
        User user = new User();
        user.setUserName("testuser");
        when(userRepository.findByUserName("testuser")).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User result = controller.addUser(user);

        assertEquals(user, result);
        verify(userRepository).save(user);
    }

    @Test
    void addUser_throwsConflict_whenUserExists() {
        User user = new User();
        user.setUserName("testuser");
        when(userRepository.findByUserName("testuser")).thenReturn(Optional.of(user));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> controller.addUser(user));
        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
    }

    @Test
    void updateUser_updatesAndReturnsUser_whenExists() {
        User existing = new User();
        existing.setUserName("testuser");
        User update = new User();
        update.setCity("New City");
        update.setState("NC");
        update.setZipcode("12345");
        update.setHasDairyAllergy(true);
        update.setHasEggAllergy(false);
        update.setHasPeanutAllergy(true);

        when(userRepository.findByUserName("testuser")).thenReturn(Optional.of(existing));
        when(userRepository.save(existing)).thenReturn(existing);

        User result = controller.updateUser("testuser", update);

        assertEquals(existing, result);
        assertEquals("New City", existing.getCity());
        assertEquals("NC", existing.getState());
        assertEquals("12345", existing.getZipcode());
        assertTrue(existing.isHasDairyAllergy());
        assertFalse(existing.isHasEggAllergy());
        assertTrue(existing.isHasPeanutAllergy());
    }

    @Test
    void updateUser_throwsNotFound_whenUserDoesNotExist() {
        when(userRepository.findByUserName("testuser")).thenReturn(Optional.empty());
        User update = new User();

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> controller.updateUser("testuser", update));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void deleteUser_deletesAndReturnsUser_whenExists() {
        User existing = new User();
        existing.setUserName("testuser");
        when(userRepository.findByUserName("testuser")).thenReturn(Optional.of(existing));

        User result = controller.deleteUser("testuser");

        assertEquals(existing, result);
        verify(userRepository).delete(existing);
    }

    @Test
    void deleteUser_throwsNotFound_whenUserDoesNotExist() {
        when(userRepository.findByUserName("testuser")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> controller.deleteUser("testuser"));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void findUser_returnsUser_whenExists() {
        User user = new User();
        user.setUserName("testuser");
        when(userRepository.findByUserName("testuser")).thenReturn(Optional.of(user));

        Optional<User> result = controller.findUser("testuser");

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void findUser_throwsNotFound_whenUsernameEmpty() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> controller.findUser(""));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void getUserReviews_returnsReviews_whenUserExists() {
        User user = new User();
        user.setUserName("testuser");
        List<DinningReview> reviews = Arrays.asList(new DinningReview());
        when(userRepository.findByUserName("testuser")).thenReturn(Optional.of(user));
        when(dinningReviewRepository.findAllByUserName("testuser")).thenReturn(reviews);

        List<DinningReview> result = controller.getUserReviews("testuser");

        assertEquals(reviews, result);
    }

    @Test
    void getUserReviews_throwsNotFound_whenUserDoesNotExist() {
        when(userRepository.findByUserName("testuser")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> controller.getUserReviews("testuser"));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
}
