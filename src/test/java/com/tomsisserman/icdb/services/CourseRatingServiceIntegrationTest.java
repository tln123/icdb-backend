package com.tomsisserman.icdb.services;

import com.tomsisserman.icdb.controllers.RatingDto;
import com.tomsisserman.icdb.entities.CourseRating;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class CourseRatingServiceIntegrationTest {
    private static final int CUSTOMER_ID = 456;
    private static final int COURSE_ID = 1;
    private static final int NOT_A_COURSE_ID = 123;

    @Autowired
    private CourseRatingService service;

    @Test
    public void createNew() {
        service.createNew(COURSE_ID, CUSTOMER_ID, 2, "it was fair");

        CourseRating newCourseRating = service.verifyCourseRating(COURSE_ID, CUSTOMER_ID);
        assertEquals(COURSE_ID, newCourseRating.getCourse().getId());
        assertEquals(CUSTOMER_ID, newCourseRating.getCustomerId());
        assertEquals(2, newCourseRating.getScore());
        assertEquals("it was fair", newCourseRating.getComment());
    }

    @Test
    public void createNewException() {
        assertThrows(NoSuchElementException.class, () -> {
            service.createNew(NOT_A_COURSE_ID, CUSTOMER_ID, 2, "it was fair");
        });
    }

    @Test
    public void update() {
        createNew();
        RatingDto courseRatingDTO = service.update(COURSE_ID, CUSTOMER_ID, 1, "one");

        assertEquals(CUSTOMER_ID, courseRatingDTO.getCustomerId());
        assertEquals(1, courseRatingDTO.getScore());
        assertEquals("one", courseRatingDTO.getComment());
    }

    @Test
    public void updateException() {
        assertThrows(NoSuchElementException.class, () -> {
            service.update(COURSE_ID, 954, 1, "one");
        });
    }

    @Test
    public void delete() {
        createNew();

        List<CourseRating> courseRatings = service.lookupAll();
        service.delete(COURSE_ID, CUSTOMER_ID);
        assertEquals(courseRatings.size() - 1, service.lookupAll().size());
    }

    @Test
    public void deleteException() {
        assertThrows(NoSuchElementException.class, () -> {
            service.delete(NOT_A_COURSE_ID, 193);
        });
    }

    @Test
    public void getAverageScore() {
        createNew();
        assertEquals(2.0, service.getAverageScore(COURSE_ID));
    }

    @Test
    public void getAverageScoreException() {
        assertThrows(NoSuchElementException.class, () -> {
            service.getAverageScore(NOT_A_COURSE_ID);
        });
    }
}
