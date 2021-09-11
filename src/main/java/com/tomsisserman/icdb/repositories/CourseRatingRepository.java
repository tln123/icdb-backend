package com.tomsisserman.icdb.repositories;

import com.tomsisserman.icdb.entities.CourseRating;
import com.tomsisserman.icdb.entities.CourseRatingPk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface CourseRatingRepository extends JpaRepository<CourseRating, Integer> {

    /**
     * lookup all the CourseRatings for a course;
     * @param courseId - is the course identifier
     *
     * @return a List of found CourseRatings
     */
    List<CourseRating> findByCourseId(Integer courseId);

    /**
     * lookup a CourseRating by the CourseId and CustomerID.
     *
     * @param courseId - course identifier.
     * @param customerId - customer identifier.
     *
     * @return Optional of found CourseRatings.
     */
    Optional<CourseRating> findByCourseIdAndCustomerId(Integer courseId, Integer customerId);

    /**
     * Fetch a PAge of CourseRatings.
     *
     * @param courseId - course identifier.
     * @param pageable - info to determine page.
     *
     * @return Page of Course Ratings.
     */
    Page<CourseRating> findByCourseId(Integer courseId, Pageable pageable);
}
