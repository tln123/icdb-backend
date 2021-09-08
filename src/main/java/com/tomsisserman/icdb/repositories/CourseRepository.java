package com.tomsisserman.icdb.repositories;

import com.tomsisserman.icdb.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends PagingAndSortingRepository<Course, Integer> {

    Page<Course> findByCourseTopicCode(@Param("code") String code, Pageable pageable);
}
