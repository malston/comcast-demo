package com.comcast.sparrow.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    GenreRepository genreRepository;

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public Iterable<Movie> movies() {
    	System.out.println("We are in the SPARROW-WEB service");
        return movieRepository.findAll();
    }

    @RequestMapping(value = "/movies/{mlId}", method = RequestMethod.GET)
    public Movie movie(@PathVariable String mlId) {
    	System.out.println("We are in the SPARROW-WEB service");
        return movieRepository.findByMlId(mlId);
    }

    @RequestMapping(value = "/movies/genre/{genreMlId}", method = RequestMethod.GET)
    public List<Movie> moviesByGenreMlId (@PathVariable String genreMlId) {
    	System.out.println("We are in the SPARROW-WEB service");
        Genre genre = genreRepository.findByMlId(genreMlId);
        return movieRepository.findByGenre(genre);
    }
}
