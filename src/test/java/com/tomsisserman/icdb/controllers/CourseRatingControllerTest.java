package com.tomsisserman.icdb.controllers;

import com.tomsisserman.icdb.entities.Course;
import com.tomsisserman.icdb.entities.CourseRating;
import com.tomsisserman.icdb.services.CourseRatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CourseRatingControllerTest {

    private static final int COURSE_ID = 999;
    private static final int CUSTOMER_ID = 1000;
    private static final int SCORE = 3;
    private static final String COMMENT = "comment";
    private static final String COURSE_RATINGS_URL = "/courses/" + COURSE_ID + "/ratings";

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CourseRatingService serviceMock;

    @Mock
    private CourseRating courseRatingMock;

    @Mock
    private Course courseMock;

    private final RatingDto ratingDto = new RatingDto(SCORE, COMMENT, CUSTOMER_ID);

    @BeforeEach
    public void setupReturnValuesOfMockMethods() {
        when(courseRatingMock.getComment()).thenReturn(COMMENT);
        when(courseRatingMock.getScore()).thenReturn(SCORE);
        when(courseRatingMock.getCustomerId()).thenReturn(CUSTOMER_ID);
        when(courseRatingMock.getCourse()).thenReturn(courseMock);
        when(courseMock.getId()).thenReturn(COURSE_ID);
    }


    @Test
    void createCourseRating() {
        restTemplate.postForEntity(COURSE_RATINGS_URL, ratingDto, Void.class);

        verify(this.serviceMock).createNew(COURSE_ID, CUSTOMER_ID, SCORE, COMMENT);
    }

    @Test
    void getRatings() {
        List<RatingDto> ListOfRatingsDto = Arrays.asList(ratingDto);
        Page<RatingDto> page = new PageImpl(ListOfRatingsDto, PageRequest.of(0, 10), 1);
        when(serviceMock.getRatings(anyInt(),any(Pageable.class))).thenReturn(page);

        ResponseEntity<String> response = restTemplate.getForEntity(COURSE_RATINGS_URL, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(serviceMock).getRatings(anyInt(), any(Pageable.class));
    }

    @Test
    void getAverage() {
        when(serviceMock.getAverageScore(COURSE_ID)).thenReturn(3.2);

        ResponseEntity<String> response = restTemplate.getForEntity(COURSE_RATINGS_URL + "/average", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"average\":3.2}", response.getBody());
    }

    @Test
    void updateWithPut() {

    }

    @Test
    void delete() {
        restTemplate.delete(COURSE_RATINGS_URL + "/" + CUSTOMER_ID);

        verify(serviceMock).delete(COURSE_ID, CUSTOMER_ID);
    }

}