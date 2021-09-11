package com.tomsisserman.icdb.controllers;

import com.tomsisserman.icdb.entities.CourseRating;
import com.tomsisserman.icdb.services.CourseRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/ratings")
public class RatingController {
    private CourseRatingService courseRatingService;

    @Autowired
    public RatingController(CourseRatingService courseRatingService) {
        this.courseRatingService = courseRatingService;
    }

    /**
     * Get all Course Ratings.
     *
     * @return list of RatingDto's of all ratings.
     */
    @GetMapping
    public List<RatingDto> getAll() {
        List<RatingDto> result = new LinkedList<>();
        for (CourseRating courseRating : courseRatingService.lookupAll())
            result.add(new RatingDto(courseRating));

        return result;
    }

    /**
     * Get RatingDto of CourseRating by the CourseRating id.
     *
     * @param id - CourseRating identifier.
     *
     * @return -  RatingDto of the CourseRating.
     */
    @GetMapping(path = "/{id}")
    public RatingDto getRating(@PathVariable("id") Integer id) {
        return new RatingDto(courseRatingService.lookupRatingById(id)
                .orElseThrow(() -> new NoSuchElementException("Rating " + id + " not found")));
    }

    /**
     * Exception handler if NoSuchElementException is thrown in this Controller
     *
     * @param ex exception
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();
    }

}
