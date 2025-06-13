package com.eduardobastos.dinningreviews.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;
    @Column(name = "name")
    private String name;

    @Column(name = "overallScore")
    private Double overallScore;

    @Column(name = "peanutScore")
    private Double peanutScore;

    @Column(name = "eggScore")
    private Double eggScore;

    @Column(name = "dairyScore")
    private Double dairyScore;

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

    public Double getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(Double overAllScore) {
        this.overallScore = overAllScore;
    }

    public Double getPeanutScore() {
        return peanutScore;
    }

    public void setPeanutScore(Double peanutScore) {
        this.peanutScore = peanutScore;
    }

    public Double getEggScore() {
        return eggScore;
    }

    public void setEggScore(Double eggScore) {
        this.eggScore = eggScore;
    }

    public Double getDairyScore() {
        return dairyScore;
    }

    public void setDairyScore(Double dairyScore) {
        this.dairyScore = dairyScore;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void recalculateScores(List<DinningReview> reviews) {
        if (reviews.isEmpty()) {
            return;
        }

        Map<String, double[]> scoreMap = new HashMap<>();
        calculateScore(reviews, "egg", scoreMap);
        calculateScore(reviews, "dairy", scoreMap);
        calculateScore(reviews, "peanut", scoreMap);

        if (scoreMap.containsKey("egg") && scoreMap.get("egg")[1] > 0) {
            this.eggScore = scoreMap.get("egg")[0] / scoreMap.get("egg")[1];
        }
        if (scoreMap.containsKey("dairy") && scoreMap.get("dairy")[1] > 0) {
            this.dairyScore = scoreMap.get("dairy")[0] / scoreMap.get("dairy")[1];
        }
        if (scoreMap.containsKey("peanut") && scoreMap.get("peanut")[1] > 0) {
            this.peanutScore = scoreMap.get("peanut")[0] / scoreMap.get("peanut")[1];
        }

        this.overallScore = scoreMap.get("egg")[0] + scoreMap.get("dairy")[0] + scoreMap.get("peanut")[0];
    }

    private void calculateScore(List<DinningReview> reviews, String allergen, Map<String, double[]> map) {
        double sum = 0.0;
        int count = 0;
        for (DinningReview review : reviews) {
            Double score = null;
            switch (allergen) {
                case "egg":
                    score = review.getEggScore();
                    break;
                case "dairy":
                    score = review.getDiaryScore();
                    break;
                case "peanut":
                    score = review.getPeanutScore();
                    break;
            }
            if (score != null && !score.isNaN()) {
                sum += score;
                count++;
            }
        }
        map.put(allergen, new double[]{sum, count});
    }
}
