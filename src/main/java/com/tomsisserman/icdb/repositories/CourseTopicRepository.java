package com.tomsisserman.icdb.repositories;

import com.tomsisserman.icdb.entities.CourseTopic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "topics", path = "topics")
public interface CourseTopicRepository extends CrudRepository<CourseTopic, String> {

    /**
     * Find Course Topic by Name
     *
     * @param name - name of the topic.
     * @return
     */
    Optional<CourseTopic> findByName(String name);
}
