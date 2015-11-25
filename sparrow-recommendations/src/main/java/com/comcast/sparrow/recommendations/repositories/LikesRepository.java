package com.comcast.sparrow.recommendations.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.comcast.sparrow.recommendations.domain.Likes;

import java.util.List;

public interface LikesRepository extends GraphRepository<Likes> {

    @Query("MATCH (m:Movie {mlId:{0}})<-[likes:LIKES]-(p:Person {userName:{1}}) return likes")
    List<Likes> likesFor(String mlId, String userName);
}
