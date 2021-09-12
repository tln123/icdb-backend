package com.tomsisserman.icdb.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RatingDtoTest {
    @Test
    public void testConstructor() {
        RatingDto dto = new RatingDto(1, "comment", 2);
        assertEquals(1, dto.getScore());
        assertEquals("comment", dto.getComment());
        assertEquals(2, dto.getCustomerId());
    }

    @Test
    public void testSetters() {
        RatingDto dto = new RatingDto();

        dto.setComment("comment");
        dto.setCustomerId(2);
        dto.setScore(1);

        assertEquals("comment", dto.getComment());
        assertEquals(2, dto.getCustomerId());
        assertEquals(1, dto.getScore());
    }

}