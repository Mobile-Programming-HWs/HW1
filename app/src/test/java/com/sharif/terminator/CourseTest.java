package com.sharif.terminator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CourseTest {
    @Test
    public void getClassTimeRangeText_formatsKnownTime() {
        Course course = course("[{\"day\":1,\"start\":9.0,\"end\":10.5}]");

        assertEquals("09:00 to 10:30", course.getClassTimeRangeText());
    }

    @Test
    public void getClassTimeRangeText_returnsFallbackForMissingTime() {
        Course course = course("[]");

        assertEquals("Unknown", course.getClassTimeRangeText());
    }

    private static Course course(String classTimes) {
        return new Course(
                "",
                "40101",
                "40101",
                "Course 40101",
                3,
                30,
                "Instructor",
                classTimes,
                40101,
                ""
        );
    }
}
