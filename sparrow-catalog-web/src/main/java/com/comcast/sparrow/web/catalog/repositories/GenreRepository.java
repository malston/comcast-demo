package com.comcast.sparrow.web.catalog.repositories;

import org.springframework.data.repository.CrudRepository;

import com.comcast.sparrow.web.catalog.domain.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    Genre findByMlId(String mlId);
}
