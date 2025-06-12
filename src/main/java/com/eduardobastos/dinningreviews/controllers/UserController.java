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

@RestController()
@RequestMapping("/users")
public class UserController {

    private UserRepository ur;
    private DinningReviewRepository drr;

    public UserController(UserRepository userRepository, DinningReviewRepository dinningReviewRepository) {
        this.ur = userRepository;
        this.drr = dinningReviewRepository;
    }

    @GetMapping("")
    public Iterable<User> getUsers() {
        return ur.findAll();
    }

    @PostMapping("")
    public User addUser(@RequestBody User user) {
        Optional<User> existingUser = ur.findByUserName(user.getUserName());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Username already exists"
            );

        }

        return ur.save(user);
    }

    @PutMapping("/{username}")
    public User updateUser(@PathVariable String username, @RequestBody User user) {
        Optional<User> userToUpdateOptional = ur.findByUserName(username);
        if (!userToUpdateOptional.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Username does not exist"
            );

        }
        User userToUpdate = userToUpdateOptional.get();
        userToUpdate.setCity(user.getCity());
        userToUpdate.setState(user.getState());
        userToUpdate.setZipcode(user.getZipcode());
        userToUpdate.setHasDairyAllergy(user.isHasDairyAllergy());
        userToUpdate.setHasEggAllergy(user.isHasEggAllergy());
        userToUpdate.setHasPeanutAllergy(user.isHasPeanutAllergy());
        User updatedUser = ur.save(userToUpdate);
        return updatedUser;
    }

    @DeleteMapping("/{username}")
    public User deleteUser(@PathVariable String username) {
        Optional<User> userToDeleteOptional = ur.findByUserName(username);
        if (!userToDeleteOptional.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Username does not exist"
            );

        }
        User userToDelete = userToDeleteOptional.get();
        ur.delete(userToDelete);
        return userToDelete;
    }

    @GetMapping("/search")
    public Optional<User> findUser(@RequestParam String username) {

        if (!username.isEmpty()) {
            return ur.findByUserName(username);
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User noy found"
        );
    }

    @GetMapping("/{username}/reviews")
    public List<DinningReview> getUserReviews(@PathVariable String username) {
        Optional<User> existingUser = ur.findByUserName(username);
        if (!existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist.");
        }
        return drr.findAllByUserName(username);

    }

}
