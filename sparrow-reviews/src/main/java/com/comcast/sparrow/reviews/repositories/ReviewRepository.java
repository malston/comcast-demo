package com.comcast.sparrow.reviews.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.comcast.sparrow.reviews.domain.Review;

public interface ReviewRepository extends MongoRepository<Review, String> {
    Iterable<Review> findByMlId(String mlId);
}
