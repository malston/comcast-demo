package com.comcast.sparrow.web;

import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    Genre findByMlId(String mlId);
}
