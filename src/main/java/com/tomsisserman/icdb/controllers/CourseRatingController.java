package com.tomsisserman.icdb.controllers;


import com.tomsisserman.icdb.services.CourseRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractMap;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/courses/{courseId}/ratings")
public class CourseRatingController {
    private CourseRatingService courseRatingService;

    @Autowired
    public CourseRatingController(CourseRatingService courseRatingService) {
        this.courseRatingService = courseRatingService;
    }

    public CourseRatingController() {

    }

    /**
     * Create a Course Rating.
     *
     * @param courseId - course identifier.
     * @param ratingDto - rating data transfer object.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCourseRating(@PathVariable(value = "courseId") int courseId, @RequestBody @Validated RatingDto ratingDto) {
        courseRatingService.createNew(courseId, ratingDto.getCustomerId(), ratingDto.getScore(), ratingDto.getComment());
    }

    /**
     * Lookup a page of Ratings for a course.
     *
     * @param courseId - Course identifier.
     * @param pageable - paging details
     *
     * @return requested page of Course ratings as RatingsDto's
     */
    @GetMapping
    public Page<RatingDto> getRatings(@PathVariable(value = "courseId") int courseId,
                                      Pageable pageable){
        return courseRatingService.getRatings(courseId, pageable);
    }

    /**
     * Calculate the average Score of a Course.
     *
     * @param courseId - course identifier.
     *
     * @return Tuple of "average" and the average value.
     */
    @GetMapping(path = "/average")
    public AbstractMap.SimpleEntry<String, Double> getAverage(@PathVariable(value = "courseId") int courseId) {
        return new AbstractMap.SimpleEntry<>("average", courseRatingService.getAverageScore(courseId));
    }

    /**
     * Update score and comment of a Course rating.
     *
     * @param courseId - course identifier.
     * @param ratingDto - rating Data Transfer Object.
     *
     * @return The modified Rating DTO.
     */
    @PutMapping
    public RatingDto updateWithPut(@PathVariable(value = "courseId") int courseId, @RequestBody @Validated RatingDto ratingDto) {
        return courseRatingService.update(courseId, ratingDto.getCustomerId(), ratingDto.getScore(), ratingDto.getComment());
    }

    /**
     * Delete a Rating of a course made by a customer.
     *
     * @param courseId - course identifier.
     * @param customerId - customer identifier.
     */
    @DeleteMapping(path = "/{customerId}")
    public void delete(@PathVariable(value = "courseId") int courseId, @PathVariable(value = "customerId") int customerId){
        courseRatingService.delete(courseId, customerId);
    }


    /**
     * Exception handler if NoSuchElementException is thrown in this Controller
     *
     * @param ex exception
     *
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();

    }

}
