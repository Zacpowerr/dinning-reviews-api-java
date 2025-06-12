package com.eduardobastos.dinningreviews.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.eduardobastos.dinningreviews.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    public Optional<User> findByUserName(String userName);

}
