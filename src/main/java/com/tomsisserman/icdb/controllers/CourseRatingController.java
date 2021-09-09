package com.tomsisserman.icdb.controllers;

import com.tomsisserman.icdb.entities.Course;
import com.tomsisserman.icdb.entities.CourseRating;
import com.tomsisserman.icdb.entities.CourseRatingPk;
import com.tomsisserman.icdb.repositories.CourseRatingRepository;
import com.tomsisserman.icdb.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/courses/{courseId}/ratings")
public class CourseRatingController {
    private CourseRatingRepository courseRatingRepository;
    private CourseRepository courseRepository;

    @Autowired
    public CourseRatingController(CourseRatingRepository courseRatingRepository, CourseRepository courseRepository) {
        this.courseRatingRepository = courseRatingRepository;
        this.courseRepository = courseRepository;
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
        Course course = verifyCourse(courseId);
        courseRatingRepository.save(new CourseRating(new CourseRatingPk(course, ratingDto.getCustomerId()),
                ratingDto.getScore(), ratingDto.getComment()));
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
        verifyCourse(courseId);
        Page<CourseRating> ratings = courseRatingRepository.findByPkCourseId(courseId, pageable);
        return new PageImpl<>(
                ratings.get().map(RatingDto::new).collect(Collectors.toList()),
                pageable,
                ratings.getTotalElements()
        );
    }

    /**
     * Calculate the average Score of a Course.
     *
     * @param courseId - course identifier.
     *
     * @return Tuple of "average" and the average value.
     */
    @GetMapping(path = "/average")
    public Map<String, Double> getAverage(@PathVariable(value = "courseId") int courseId) {
        verifyCourse(courseId);
        return Map.of("average", courseRatingRepository.findByPkCourseId(courseId).stream()
                .mapToInt(CourseRating::getScore).average()
                .orElseThrow(() ->
                        new NoSuchElementException("Course has no Ratings")));
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
        CourseRating rating = verifyCourseRating(courseId, ratingDto.getCustomerId());
        rating.setScore(ratingDto.getScore());
        rating.setComment(ratingDto.getComment());
        return new RatingDto(courseRatingRepository.save(rating));
    }

    /**
     * Delete a Rating of a course made by a customer.
     *
     * @param courseId - course identifier.
     * @param customerId - customer identifier.
     */
    @DeleteMapping(path = "/{customerId}")
    public void delete(@PathVariable(value = "courseId") int courseId, @PathVariable(value = "customerId") int customerId){
        CourseRating rating = verifyCourseRating(courseId, customerId);
        courseRatingRepository.delete(rating);
    }

    /**
     * Verify and return the CourseRating for a particular courseId and CustomerId.
     *
     * @param courseId - course identifier.
     * @param customerId - customer identifier.
     *
     * @return the found CourseRating.
     *
     * @throws NoSuchElementException if no CourseRating found.
     */
    private CourseRating verifyCourseRating(int courseId, int customerId) throws NoSuchElementException {
        return courseRatingRepository.findByPkCourseIdAndPkCustomerId(courseId, customerId).orElseThrow(() ->
                new NoSuchElementException("Course-Rating pair with course id " + courseId +
                        " and customer id " + customerId + " doesnt exists"));
    }

    /**
     * Verify and return the Course given a courseId
     *
     * @param courseId - course identifier.
     *
     * @return - the found course
     *
     * @throws NoSuchElementException if no Course found.
     */
    private Course verifyCourse(int courseId) throws NoSuchElementException {
        return courseRepository.findById(courseId).orElseThrow(() ->
                new NoSuchElementException("Course does not exist " + courseId));
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
