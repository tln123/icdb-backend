package com.tomsisserman.icdb;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomsisserman.icdb.entities.Difficulty;
import com.tomsisserman.icdb.entities.Platform;
import com.tomsisserman.icdb.services.CourseService;
import com.tomsisserman.icdb.services.CourseTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

@SpringBootApplication
public class IcdbApplication implements CommandLineRunner {

    @Value("tempCourses.json")
    private String importFile;

    @Autowired
    private CourseTopicService courseTopicService;

    @Autowired
    private CourseService courseService;

    public static void main(String[] args) {
        SpringApplication.run(IcdbApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception{
        createAllCoursesTopics();
        createCourses(importFile);
    }

    /**
     * Initialize the courses topics.
     */
    private void createAllCoursesTopics(){
        courseTopicService.createCourseTopic("SPG", "Spring");
        courseTopicService.createCourseTopic("OOP", "OOP");
        courseTopicService.createCourseTopic("DP", "Design Patterns");
        courseTopicService.createCourseTopic("SGT", "Spring Testing");
        courseTopicService.createCourseTopic("K8S", "Kubernetes");
    }

    /**
     * Create course entities from external file.
     *
     * @param fileToImport
     * @throws IOException
     */
    private void createCourses(String fileToImport) throws IOException {
        CourseFromFile.read(fileToImport).forEach(importedCourse ->
                courseService.createCourse(importedCourse.getTitle(),
                        importedCourse.getDescription(),
                        importedCourse.getDurationInHours(),
                        importedCourse.getPrice(),
                        importedCourse.getLecturer(),
                        importedCourse.getCourseTopic(),
                        importedCourse.getDifficulty(),
                        importedCourse.getPlatform()));
    }

    /**
     * Helper class to import tempCourses.json
     */
    private static class CourseFromFile {
        //fields
        private String courseTopic, title, description, durationInHours,
                price, lecturer, difficulty, platform;
        //reader
        static List<CourseFromFile> read(String fileToImport) throws IOException {
            return new ObjectMapper().setVisibility(FIELD, ANY).
                    readValue(new FileInputStream(fileToImport), new TypeReference<List<CourseFromFile>>() {});
        }
        protected CourseFromFile(){}

        String getCourseTopic() { return courseTopic; }

        String getTitle() { return title; }

        String getDescription() { return description; }

        Integer getPrice() { return Integer.parseInt(price); }

        Integer getDurationInHours() { return Integer.parseInt(durationInHours); }

        String getLecturer() { return lecturer; }

        Difficulty getDifficulty() { return Difficulty.valueOf(difficulty); }

        Platform getPlatform() { return Platform.valueOf(platform); }
    }
}
