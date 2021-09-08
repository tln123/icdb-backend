package com.tomsisserman.icdb.services;

import com.tomsisserman.icdb.entities.Course;
import com.tomsisserman.icdb.entities.CourseTopic;
import com.tomsisserman.icdb.entities.Difficulty;
import com.tomsisserman.icdb.entities.Platform;
import com.tomsisserman.icdb.repositories.CourseRepository;
import com.tomsisserman.icdb.repositories.CourseTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private CourseRepository courseRepository;
    private CourseTopicRepository courseTopicRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, CourseTopicRepository courseTopicRepository) {
        this.courseRepository = courseRepository;
        this.courseTopicRepository = courseTopicRepository;
    }

    public Course createCourse(String title, String description, Integer durationInHours, Integer price, String lecturer, String courseTopicName, Difficulty difficulty, Platform platform) {
        CourseTopic courseTopic = courseTopicRepository.findByName(courseTopicName)
                .orElseThrow(() -> new RuntimeException("Course Topic does not exist: " + courseTopicName));

        return courseRepository.save(new Course(title, description, durationInHours, price, lecturer, courseTopic, difficulty, platform));
    }

    public long total() {
        return courseRepository.count();
    }

}
