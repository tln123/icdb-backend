package com.tomsisserman.icdb.controllers;

import com.tomsisserman.icdb.entities.CourseRating;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RatingDto {

    @Min(0)
    @Max(5)
    private Integer score;

    @Size(max = 255)
    private String comment;

    @NotNull
    private Integer customerId;

    /**
     * Constructor for RatingDto
     *
     * @param score      - score 1-5
     * @param comment    - comment
     * @param customerId - customer identifier
     */
    public RatingDto(Integer score, String comment, Integer customerId) {
        this.score = score;
        this.comment = comment;
        this.customerId = customerId;
    }

    protected RatingDto() {
    }

    /**
     * Construct a RatingDto from a CourseRating.
     *
     * @param courseRating - Course Rating Object
     */
    public RatingDto(CourseRating courseRating) {
            this(courseRating.getScore(), courseRating.getComment(), courseRating.getCustomerId());
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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
