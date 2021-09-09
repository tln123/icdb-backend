package com.tomsisserman.icdb.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseRatingTest {

    private Course course = new Course("Spring Framework 5", "description test", 27, 500, "John", new CourseTopic("SPG", "Spring"), Difficulty.Hard, Platform.Udemy);

    @Test
    public void testConstructor() {
        CourseRating rating = new CourseRating(new CourseRatingPk(course, 1), 1, "comment");
        assertEquals("comment" ,rating.getComment());
        assertEquals( course, rating.getPk().getCourse());
        assertEquals(1, rating.getScore());
        assertEquals(1, rating.getPk().getCustomerId());
    }

    @Test
    public void equalsHashcodeVerify(){
        CourseRating rating1 = new CourseRating(new CourseRatingPk(course, 1), 1, "comment");
        CourseRating rating2 = new CourseRating(new CourseRatingPk(course, 1), 1, "comment");

        assertEquals(rating1, rating2);
        assertEquals(rating1.hashCode(), rating2.hashCode());
    }

}
