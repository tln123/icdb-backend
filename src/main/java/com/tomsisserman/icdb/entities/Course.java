package com.tomsisserman.icdb.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Course {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String title;

    @Column(length = 2000)
    private String description;

    @Column
    private Integer durationInHours;

    @Column
    private Integer price;

    @Column
    private String lecturer;

    @ManyToOne
    private CourseTopic courseTopic;

    @Column
    @Enumerated
    private Difficulty difficulty;

    @Column
    @Enumerated
    private Platform platform;


    protected Course() {
    }

    public Course(String title, String description, Integer durationInHours, Integer price, String lecturer, CourseTopic courseTopic ,Difficulty difficulty, Platform platform) {
        this.title = title;
        this.description = description;
        this.durationInHours = durationInHours;
        this.price = price;
        this.lecturer = lecturer;
        this.courseTopic = courseTopic;
        this.difficulty = difficulty;
        this.platform = platform;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDurationInHours() {
        return durationInHours;
    }

    public void setDurationInHours(Integer durationInHours) {
        this.durationInHours = durationInHours;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public CourseTopic getCourseTopic() {
        return courseTopic;
    }

    public void setCourseTopic(CourseTopic courseTopic) {
        this.courseTopic = courseTopic;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", durationInHours=" + durationInHours +
                ", price=" + price +
                ", Lecturer='" + lecturer + '\'' +
                ", courseTopic=" + courseTopic +
                ", difficulty=" + difficulty +
                ", platform=" + platform +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) &&
               Objects.equals(title, course.title) &&
               Objects.equals(description, course.description) &&
               Objects.equals(durationInHours, course.durationInHours) &&
               Objects.equals(price, course.price) &&
               Objects.equals(lecturer, course.lecturer) &&
               Objects.equals(courseTopic, course.courseTopic) &&
               difficulty == course.difficulty &&
               platform == course.platform;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, durationInHours, price, lecturer, courseTopic, difficulty, platform);
    }
}
