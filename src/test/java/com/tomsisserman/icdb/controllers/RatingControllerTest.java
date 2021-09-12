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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RatingControllerTest {
    private static final String RATINGS_URL = "/ratings";
    private static final int COURSE_ID = 999;
    private static final int RATING_ID = 555;
    private static final int CUSTOMER_ID = 1000;
    private static final int SCORE = 3;
    private static final String COMMENT = "comment";

    @MockBean
    private CourseRatingService courseRatingServiceMock;

    @Mock
    private CourseRating courseRatingMock;

    @Mock
    private Course courseMock;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setupReturnValuesOfMockMethods() {
        when(courseRatingMock.getCourse()).thenReturn(courseMock);
        when(courseMock.getId()).thenReturn(COURSE_ID);
        when(courseRatingMock.getComment()).thenReturn(COMMENT);
        when(courseRatingMock.getScore()).thenReturn(SCORE);
        when(courseRatingMock.getCustomerId()).thenReturn(CUSTOMER_ID);
    }

    @Test
    public void getAll() {
        when(courseRatingServiceMock.lookupAll()).thenReturn(Arrays.asList(courseRatingMock, courseRatingMock, courseRatingMock));
        
        ResponseEntity<List<RatingDto>> response = restTemplate.exchange(RATINGS_URL, HttpMethod.GET,null,
                new ParameterizedTypeReference<>() {});
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
    }

    @Test
    public void getRating() {
        when(courseRatingServiceMock.lookupRatingById(RATING_ID)).thenReturn(Optional.of(courseRatingMock));

        ResponseEntity<RatingDto> response =
                restTemplate.getForEntity(RATINGS_URL + "/" + RATING_ID, RatingDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CUSTOMER_ID, response.getBody().getCustomerId());
        assertEquals(COMMENT, response.getBody().getComment());
        assertEquals(SCORE, response.getBody().getScore());
    }
}