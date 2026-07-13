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

    @Test
    public void summaryText_includesCourseNumberUnitsAndCapacity() {
        Course course = course("[{\"day\":1,\"start\":9.0,\"end\":10.5}]");

        assertEquals("40101 | 3 units | Capacity 30", course.getSummaryText());
    }

    @Test
    public void summaryText_hidesInvalidCapacity() {
        Course course = course("[{\"day\":1,\"start\":9.0,\"end\":10.5}]", -1);

        assertEquals("40101 | 3 units | Capacity not set", course.getSummaryText());
    }

    @Test
    public void scheduleSummaryText_includesDaysAndTime() {
        Course course = course("[{\"day\":1,\"start\":9.0,\"end\":10.5},{\"day\":3,\"start\":9.0,\"end\":10.5}]");

        assertEquals("Sunday, Tuesday | 09:00 to 10:30", course.getScheduleSummaryText());
    }

    private static Course course(String classTimes) {
        return course(classTimes, 30);
    }

    private static Course course(String classTimes, int capacity) {
        return new Course(
                "",
                "40101",
                "40101",
                "Course 40101",
                3,
                capacity,
                "Instructor",
                classTimes,
                40101,
                ""
        );
    }
}
