package com.tomsisserman.icdb.repositories;

import com.tomsisserman.icdb.entities.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Integer> {
}
