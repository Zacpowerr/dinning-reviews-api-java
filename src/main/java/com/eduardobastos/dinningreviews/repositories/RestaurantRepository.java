package com.eduardobastos.dinningreviews.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.eduardobastos.dinningreviews.models.Restaurant;

public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {

    public List<Restaurant> countByNameAndZipCode(String name, String zipcode);

    public List<Restaurant> findAllByZipCode(String zipcode);
}
