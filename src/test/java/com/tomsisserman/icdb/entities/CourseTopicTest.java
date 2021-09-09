package com.tomsisserman.icdb.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseTopicTest {

    @Test
    public void testConstructorAndGetters(){
        CourseTopic topic = new CourseTopic("SPG", "Spring");
        assertEquals("SPG", topic.getCode());
        assertEquals("Spring", topic.getName());
    }

    @Test
    public void equalHashcodeVerify() {
        CourseTopic topic1 = new CourseTopic("SPG", "Spring");
        CourseTopic topic2 = new CourseTopic("SPG", "Spring");

        assertEquals(topic1, topic2);
        assertEquals(topic1.hashCode(), topic2.hashCode());
    }
}
