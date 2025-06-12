package com.eduardobastos.dinningreviews.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dinning_reviews")
public class DinningReview {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "restaurant_id")
    private Integer restaurantId;

    @Column(name = "peanut_score")
    private Float peanutScore;

    @Column(name = "egg_score")
    private Float eggScore;

    @Column(name = "diary_score")
    private Float diaryScore;

    @Column(name = "details")
    private String details;

    @Column(name = "status")
    private DinningReviewStatus status;

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

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
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

    public Float getDiaryScore() {
        return diaryScore;
    }

    public void setDiaryScore(Float diaryScore) {
        this.diaryScore = diaryScore;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public DinningReviewStatus getStatus() {
        return status;
    }

    public void setStatus(DinningReviewStatus status) {
        this.status = status;
    }

}
