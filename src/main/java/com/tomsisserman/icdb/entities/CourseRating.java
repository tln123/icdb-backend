package com.tomsisserman.icdb.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import java.util.Objects;

public class CourseRating {

    @EmbeddedId
    private CourseRatingPk pk;

    @Column(nullable = false)
    private Integer score;

    @Column
    private String comment;

    /**
     * Create a fully initialized CourseRating
     *
     * @param pk - primary key of a course and customer id.
     * @param score - Integer score (1-5).
     * @param comment - Optional comment from the user.
     */
    public CourseRating(CourseRatingPk pk, Integer score, String comment) {
        this.pk = pk;
        this.score = score;
        this.comment = comment;
    }

    protected CourseRating() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseRating that = (CourseRating) o;
        return Objects.equals(pk, that.pk) && Objects.equals(score, that.score) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, score, comment);
    }

    @Override
    public String toString() {
        return "CourseRating{" +
                "pk=" + pk +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                '}';
    }

    public CourseRatingPk getPk() {
        return pk;
    }

    public void setPk(CourseRatingPk pk) {
        this.pk = pk;
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
}
