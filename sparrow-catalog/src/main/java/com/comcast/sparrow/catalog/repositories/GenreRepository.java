package com.comcast.sparrow.catalog.repositories;

import org.springframework.data.repository.CrudRepository;

import com.comcast.sparrow.catalog.domain.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    Genre findByMlId(String mlId);
}
