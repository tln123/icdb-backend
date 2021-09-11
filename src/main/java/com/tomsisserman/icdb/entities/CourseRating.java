package com.tomsisserman.icdb.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class CourseRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Course course;

    @Column
    private Integer customerId;

    @Column(nullable = false)
    private Integer score;

    @Column
    private String comment;

    protected CourseRating() {
    }

    /**
     * Create a fully initialized CourseRating
     *
     * @param course - the course.
     * @param customerId - the customer identifier.
     * @param score - Integer score (1-5).
     * @param comment - Optional comment from the user.
     */
    public CourseRating(Course course, Integer customerId, Integer score, String comment) {
        this.course = course;
        this.customerId = customerId;
        this.score = score;
        this.comment = comment;
    }

    public CourseRating(Course course, Integer customerId, Integer score) {
        this.course = course;
        this.customerId = customerId;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseRating that = (CourseRating) o;
        return Objects.equals(id, that.id) && Objects.equals(course, that.course) && Objects.equals(customerId, that.customerId) && Objects.equals(score, that.score) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, course, customerId, score, comment);
    }

    @Override
    public String toString() {
        return "CourseRating{" +
                "id=" + id +
                ", course=" + course +
                ", customerId=" + customerId +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                '}';
    }
}
