package com.eduardobastos.dinningreviews.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "name")
    private String name;

    @Column(name = "overallScore")
    private Float overallScore;

    @Column(name = "peanutScore")
    private Float peanutScore;

    @Column(name = "eggScore")
    private Float eggScore;

    @Column(name = "dairyScore")
    private Float dairyScore;

    @Column(name = "zip_code")
    private String zipCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(Float overAllScore) {
        this.overallScore = overAllScore;
    }

    public Float getPeanutScore() {
        return peanutScore;
    }

    public void setPeanutScore(Float peanutScore) {
        this.peanutScore = peanutScore;
    }

    public Float getEggScore() {
        return eggScore;
    }

    public void setEggScore(Float eggScore) {
        this.eggScore = eggScore;
    }

    public Float getDairyScore() {
        return dairyScore;
    }

    public void setDairyScore(Float dairyScore) {
        this.dairyScore = dairyScore;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
