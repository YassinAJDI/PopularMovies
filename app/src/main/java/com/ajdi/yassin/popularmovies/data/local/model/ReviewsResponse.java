package com.ajdi.yassin.popularmovies.data.local.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Yassin Ajdi
 * @since 11/13/2018.
 */
public class ReviewsResponse {

    @SerializedName("results")
    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
