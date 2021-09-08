package com.tomsisserman.icdb.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

/**
 * Course Rating Primary Key containing a course and a customer identifier.
 */
@Embeddable
public class CourseRatingPk implements Serializable {
    @ManyToOne
    private Course course;

    @Column(insertable = false, updatable = false, nullable = false)
    private Integer customerId;

    public CourseRatingPk() {
    }

    /**
     * Initialize a Course Rating PK
     * @param course - the Course
     * @param customerId - the customer identifier
     */
    public CourseRatingPk(Course course, Integer customerId) {
        this.course = course;
        this.customerId = customerId;
    }

    public Course getCourse() {
        return course;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseRatingPk that = (CourseRatingPk) o;
        return Objects.equals(course, that.course) && Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, customerId);
    }

    @Override
    public String toString() {
        return "CourseRatingPk{" +
                "course=" + course +
                ", customerId=" + customerId +
                '}';
    }
}
