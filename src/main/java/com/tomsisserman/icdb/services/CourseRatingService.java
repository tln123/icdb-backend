package com.tomsisserman.icdb.services;

import com.tomsisserman.icdb.controllers.RatingDto;
import com.tomsisserman.icdb.entities.Course;
import com.tomsisserman.icdb.entities.CourseRating;
import com.tomsisserman.icdb.repositories.CourseRatingRepository;
import com.tomsisserman.icdb.repositories.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class CourseRatingService {
    private CourseRepository courseRepository;
    private CourseRatingRepository courseRatingRepository;

    /**
     * Construct CourseRatingService
     *
     * @param courseRepository       - Course Repository.
     * @param courseRatingRepository - CourseRating Repository.
     */
    public CourseRatingService(CourseRepository courseRepository, CourseRatingRepository courseRatingRepository) {
        this.courseRepository = courseRepository;
        this.courseRatingRepository = courseRatingRepository;
    }

    /**
     * Create a new Course Rating in the DB.
     *
     * @param courseId - course identifier.
     * @param customerId customer identifier
     * @param score score of the course rating
     * @param comment additional comment
     *
     * @throws NoSuchElementException if no Course found.
     */
    public void createNew(int courseId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
        courseRatingRepository.save(new CourseRating(verifyCourse(courseId), customerId, score, comment));
    }

    /**
     * Get a ratings by id.
     *
     * @param id rating identifier
     * @return TourRatings
     */
    public Optional<CourseRating> lookupRatingById(int id)  {
        return courseRatingRepository.findById(id);
    }

    /**
     * Get All Ratings.
     *
     * @return List of TourRatings
     */
    public List<CourseRating> lookupAll()  {
        return courseRatingRepository.findAll();
    }

    /**
     * Get a page of a course ratings for a Course.
     *
     * @param courseId - course identifier.
     * @param pageable - page parameters to determine which elements to fetch.
     *
     * @return - Page of Course Ratings.
     *
     * @throws NoSuchElementException if no Course found.
     */
    public Page<RatingDto> getRatings(int courseId, Pageable pageable) throws NoSuchElementException {
        verifyCourse(courseId);
        Page<CourseRating> ratings = courseRatingRepository.findByCourseId(courseId, pageable);
        return new PageImpl<>(
                ratings.get().map(RatingDto::new).collect(Collectors.toList()),
                pageable,
                ratings.getTotalElements()
        );
    }

    /**
     * Get the average score of a course.
     *
     * @param courseId - course identifier.
     *
     * @return the average score as a Double.
     *
     * @throws NoSuchElementException if Course doesn't exist.
     */
    public Double getAverageScore(int courseId) throws NoSuchElementException {
        List<CourseRating> ratings = courseRatingRepository.findByCourseId(verifyCourse(courseId).getId());
        OptionalDouble average = ratings.stream().mapToInt((rating) -> rating.getScore()).average();
        return average.isPresent() ? average.getAsDouble() : null;
    }

    /**
     * Update the elements of a Course ratings.
     *
     * @param courseId - course identifier.
     * @param score score of the tour rating
     * @param comment additional comment
     *
     * @return Rating Data Transfer Object.
     * @throws NoSuchElementException if no Course found.
     */
    public RatingDto update(int courseId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
        CourseRating rating = verifyCourseRating(courseId, customerId);
        rating.setScore(score);
        rating.setComment(comment);
        return new RatingDto(courseRatingRepository.save(rating));
    }

    /**
     * Update all of the elements of a Tour Rating.
     *
     * @param courseId course identifier
     * @param customerId customer identifier
     * @param score score of the course rating
     * @param comment additional comment
     *
     * @return courseRating
     *
     * @throws NoSuchElementException if no Course found.
     */
    public CourseRating updateSome(int courseId, Integer customerId, Integer score, String comment)
            throws NoSuchElementException {
        CourseRating rating = verifyCourseRating(courseId, customerId);
        if (score != null) {
            rating.setScore(score);
        }
        if (comment!= null) {
            rating.setComment(comment);
        }
        return courseRatingRepository.save(rating);
    }

    /**
     * Delete a Course Rating.
     *
     * @param courseId - course identifier.
     * @param customerId - customer identifier.
     *
     * @throws NoSuchElementException if no Course found.
     */
    public void delete(int courseId, int customerId) throws NoSuchElementException {
        CourseRating rating = verifyCourseRating(courseId, customerId);
        courseRatingRepository.delete(rating);
    }

    /**
     * Verify and return the CourseRating for a particular courseId and CustomerId.
     *
     * @param courseId   - course identifier.
     * @param customerId - customer identifier.
     * @return the found CourseRating.
     * @throws NoSuchElementException if no CourseRating found.
     */
    public CourseRating verifyCourseRating(int courseId, int customerId) throws NoSuchElementException {
        return courseRatingRepository.findByCourseIdAndCustomerId(courseId, customerId).orElseThrow(() ->
                new NoSuchElementException("Course-Rating pair with course id " + courseId +
                        " and customer id " + customerId + " doesnt exists"));
    }

    /**
     * Verify and return the Course given a courseId
     *
     * @param courseId - course identifier.
     * @return - the found course
     * @throws NoSuchElementException if no Course found.
     */
    private Course verifyCourse(int courseId) throws NoSuchElementException {
        return courseRepository.findById(courseId).orElseThrow(() ->
                new NoSuchElementException("Course does not exist " + courseId));
    }

}
