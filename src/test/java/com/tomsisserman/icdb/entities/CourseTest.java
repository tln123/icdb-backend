package com.tomsisserman.icdb.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {

    @Test
    public void testConstructorAndGetters(){
        CourseTopic topic = new CourseTopic("SPG", "Spring");
        Course course = new Course("Spring Framework 5", "description test", 27, 500, "John", topic, Difficulty.Hard, Platform.Udemy);

        assertAll("Course Test Props",
                () -> assertNull(course.getId()),
                () -> assertEquals("Spring Framework 5", course.getTitle()),
                () -> assertEquals("description test", course.getDescription()),
                () -> assertEquals(27, course.getDurationInHours()),
                () -> assertEquals(500, course.getPrice()),
                () -> assertEquals("John", course.getLecturer()),
                () -> assertEquals("SPG", course.getCourseTopic().getCode()),
                () -> assertEquals(Difficulty.Hard, course.getDifficulty()),
                () -> assertEquals(Platform.Udemy, course.getPlatform())
        );
    }

    @Test
    public void equalsHashcodeVerify() {
        CourseTopic topic = new CourseTopic("SPG", "Spring");
        Course course1 = new Course("Spring Framework 5", "description test", 27, 500, "John", topic, Difficulty.Hard, Platform.Udemy);
        Course course2 = new Course("Spring Framework 5", "description test", 27, 500, "John", topic, Difficulty.Hard, Platform.Udemy);

        assertEquals(course1, course2);
        assertEquals(course1.hashCode(), course2.hashCode());
    }



}
