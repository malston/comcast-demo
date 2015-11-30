package com.comcast.sparrow.apigateway.domain;

import java.util.List;

import com.comcast.sparrow.apigateway.services.catalog.Genre;
import com.comcast.sparrow.apigateway.services.recommendations.Movie;
import com.comcast.sparrow.apigateway.services.reviews.Review;

public class MovieDetails {
    private String title;
    private String mlId;
    private List<Review> reviews;
    private List<Genre> genres;
    private List<Movie> recommendations;
    private Boolean likes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMlId() {
        return mlId;
    }

    public void setMlId(String mlId) {
        this.mlId = mlId;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setRecommendations(List<Movie> recommendations) {
        this.recommendations = recommendations;
    }

    public List<Movie> getRecommendations() {
        return recommendations;
    }

    public void setLikes(Boolean likes) {
        this.likes = likes;
    }

    public Boolean getLikes() {
        return likes;
    }
}
