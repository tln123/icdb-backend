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

    /**
     * Create a Course Topic.
     *
     * @param code code of the Topic
     * @param name name of the Topic
     *
     * @return new or existing tour package.
     */
    public CourseTopic createCourseTopic(String code, String name) {
        return courseTopicRepository.findById(code).orElse(courseTopicRepository.save(new CourseTopic(code, name)));
    }

    /**
     * Lookup all course topics.
     *
     * @return
     */
    public Iterable<CourseTopic> lookup() {
        return courseTopicRepository.findAll();
    }

    /**
     * Calculate the number of topics in the DB.
     *
     * @return the total of topics.
     */
    public long total() {
        return courseTopicRepository.count();
    }
}
