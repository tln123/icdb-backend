package com.tomsisserman.icdb.services;

import com.tomsisserman.icdb.entities.Course;
import com.tomsisserman.icdb.entities.CourseRating;
import com.tomsisserman.icdb.repositories.CourseRatingRepository;
import com.tomsisserman.icdb.repositories.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseRatingServiceTest {

    private static final int CUSTOMER_ID = 123;
    private static final int COURSE_ID = 1;
    private static final int COURSE_RATING_ID = 100;

    @Mock
    private CourseRepository courseRepositoryMock;

    @Mock
    private CourseRatingRepository courseRatingRepositoryMock;

    @InjectMocks
    private CourseRatingService service;

    @Mock
    private Course courseMock;

    @Mock
    private CourseRating courseRatingMock;


    @Test
    public void createNew() {
        when(courseRepositoryMock.findById(COURSE_ID)).thenReturn(Optional.of(courseMock));

        //prepare to capture a CourseRating Object
        ArgumentCaptor<CourseRating> courseRatingCaptor = ArgumentCaptor.forClass(CourseRating.class);

        //invoke createNew
        service.createNew(COURSE_ID, CUSTOMER_ID, 2, "ok");

        //verify tourRatingRepository.save invoked once and capture the TourRating Object
        verify(courseRatingRepositoryMock).save(courseRatingCaptor.capture());

        //verify the attributes of the Tour Rating Object
        assertEquals(courseMock, courseRatingCaptor.getValue().getCourse());
        assertEquals(CUSTOMER_ID, courseRatingCaptor.getValue().getCustomerId());
        assertEquals(2, courseRatingCaptor.getValue().getScore());
        assertEquals("ok", courseRatingCaptor.getValue().getComment());
    }

    @Test
    public void lookupRatingById() {
        when(courseRatingRepositoryMock.findById(COURSE_RATING_ID)).thenReturn(Optional.of(courseRatingMock));

        assertEquals(courseRatingMock, service.lookupRatingById(COURSE_RATING_ID).get());
    }

    @Test
    public void lookupAll() {
        when(courseRatingRepositoryMock.findAll()).thenReturn(Arrays.asList(courseRatingMock));

        assertEquals(courseRatingMock, service.lookupAll().get(0));
    }


    @Test
    public void getAverageScore() {
        when(courseRepositoryMock.findById(COURSE_ID)).thenReturn(Optional.of(courseMock));
        when(courseMock.getId()).thenReturn(COURSE_ID);
        when(courseRatingRepositoryMock.findByCourseId(COURSE_ID)).thenReturn(Arrays.asList(courseRatingMock));
        when(courseRatingMock.getScore()).thenReturn(10);

        //invoke and verify getAverageScore
        assertEquals(10.0, service.getAverageScore(COURSE_ID));
    }


    // FIX
    @Test
    public void update() {
        when(courseRatingRepositoryMock.findByCourseIdAndCustomerId(COURSE_ID,CUSTOMER_ID)).thenReturn(Optional.of(courseRatingMock));
        when(courseRatingRepositoryMock.save(courseRatingMock)).thenReturn(courseRatingMock);

        service.update(COURSE_ID,CUSTOMER_ID,5, "great");

        //verify tourRatingRepository.save invoked once
        verify(courseRatingRepositoryMock).save(any(CourseRating.class));

        //verify and tourRating setter methods invoked
        verify(courseRatingMock).setComment("great");
        verify(courseRatingMock).setScore(5);
    }


    @Test
    public void delete() {
        when(courseRatingRepositoryMock.findByCourseIdAndCustomerId(COURSE_ID,CUSTOMER_ID)).thenReturn(Optional.of(courseRatingMock));

        service.delete(COURSE_ID, CUSTOMER_ID);

        verify(courseRatingRepositoryMock).delete(any(CourseRating.class));
    }
}