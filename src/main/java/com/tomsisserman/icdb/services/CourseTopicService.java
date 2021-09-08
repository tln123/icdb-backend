package com.tomsisserman.icdb.services;

import com.tomsisserman.icdb.entities.CourseTopic;
import com.tomsisserman.icdb.repositories.CourseTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseTopicService {
    private CourseTopicRepository courseTopicRepository;

    @Autowired
    public CourseTopicService(CourseTopicRepository courseTopicRepository) {
        this.courseTopicRepository = courseTopicRepository;
    }

    public CourseTopic createCourseTopic(String code, String name) {
        return courseTopicRepository.findById(code).orElse(courseTopicRepository.save(new CourseTopic(code, name)));
    }

    public Iterable<CourseTopic> lookup() {
        return courseTopicRepository.findAll();
    }

    public long total() {
        return courseTopicRepository.count();
    }
}
