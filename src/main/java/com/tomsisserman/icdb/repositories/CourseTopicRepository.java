package com.tomsisserman.icdb.repositories;

import com.tomsisserman.icdb.entities.CourseTopic;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CourseTopicRepository extends CrudRepository<CourseTopic, String> {

    Optional<CourseTopic> findByName(String name);
}
