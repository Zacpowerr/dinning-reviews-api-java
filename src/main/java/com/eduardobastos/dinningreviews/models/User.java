package com.eduardobastos.dinningreviews.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "hasPeanutAllergy")
    private boolean hasPeanutAllergy;

    @Column(name = "hasEggAllergy")
    private boolean hasEggAllergy;

    @Column(name = "hasDairyAllergy")
    private boolean hasDairyAllergy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public boolean isHasPeanutAllergy() {
        return hasPeanutAllergy;
    }

    public void setHasPeanutAllergy(boolean hasPeanutAllergy) {
        this.hasPeanutAllergy = hasPeanutAllergy;
    }

    public boolean isHasEggAllergy() {
        return hasEggAllergy;
    }

    public void setHasEggAllergy(boolean hasEggAllergy) {
        this.hasEggAllergy = hasEggAllergy;
    }

    public boolean isHasDairyAllergy() {
        return hasDairyAllergy;
    }

    public void setHasDairyAllergy(boolean hasDairyAllergy) {
        this.hasDairyAllergy = hasDairyAllergy;
    }

}
