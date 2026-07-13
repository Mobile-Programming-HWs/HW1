package com.sharif.terminator;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SelectedCourseTest {
    @Before
    public void clearSelectedCourses() {
        SelectedCourse.clearSelectedCourses();
    }

    @Test
    public void addSelectedCourse_addsBothMeetingsAndSortsThem() {
        Course course = course("40101", "[{\"day\":3,\"start\":10.0,\"end\":11.5},{\"day\":1,\"start\":10.0,\"end\":11.5}]", "");

        assertTrue(SelectedCourse.addSelectedCourse(course));

        ArrayList<Course> selectedCourses = SelectedCourse.getSelectedCourses();
        assertEquals(2, selectedCourses.size());
        assertEquals(1, selectedCourses.get(0).getClassDate());
        assertEquals(3, selectedCourses.get(1).getClassDate());
        assertEquals(10.0f, selectedCourses.get(0).getClassTimeBeginning(), 0.001f);
        assertEquals(11.5f, selectedCourses.get(1).getClassTimeEnding(), 0.001f);
    }

    @Test
    public void addSelectedCourse_rejectsDuplicateCourseNumber() {
        Course firstCourse = course("40101", "[{\"day\":1,\"start\":8.0,\"end\":9.0}]", "");
        Course duplicateCourse = course("40101", "[{\"day\":2,\"start\":10.0,\"end\":11.0}]", "");

        assertTrue(SelectedCourse.addSelectedCourse(firstCourse));
        assertEquals(SelectedCourse.AddCourseStatus.DUPLICATE_COURSE, SelectedCourse.addSelectedCourseWithStatus(duplicateCourse));
        assertFalse(SelectedCourse.addSelectedCourse(duplicateCourse));

        assertEquals(1, SelectedCourse.getSelectedCourses().size());
    }

    @Test
    public void addSelectedCourse_rejectsExamConflictAfterTrimmingWhitespace() {
        Course firstCourse = course("40101", "[{\"day\":1,\"start\":8.0,\"end\":9.0}]", "1403-10-01 08:00");
        Course conflictingExamCourse = course("40102", "[{\"day\":2,\"start\":10.0,\"end\":11.0}]", " 1403-10-01 08:00 ");

        assertTrue(SelectedCourse.addSelectedCourse(firstCourse));
        assertEquals(SelectedCourse.AddCourseStatus.EXAM_CONFLICT, SelectedCourse.addSelectedCourseWithStatus(conflictingExamCourse));
        assertFalse(SelectedCourse.addSelectedCourse(conflictingExamCourse));

        assertEquals(1, SelectedCourse.getSelectedCourses().size());
    }

    @Test
    public void addSelectedCourse_rejectsOverlappingClassTimeOnSameDay() {
        Course firstCourse = course("40101", "[{\"day\":1,\"start\":9.0,\"end\":10.5}]", "");
        Course overlappingCourse = course("40102", "[{\"day\":1,\"start\":10.0,\"end\":11.0}]", "");

        assertTrue(SelectedCourse.addSelectedCourse(firstCourse));
        assertEquals(SelectedCourse.AddCourseStatus.CLASS_TIME_CONFLICT, SelectedCourse.addSelectedCourseWithStatus(overlappingCourse));
        assertFalse(SelectedCourse.addSelectedCourse(overlappingCourse));

        assertEquals(1, SelectedCourse.getSelectedCourses().size());
    }

    @Test
    public void addSelectedCourse_allowsBackToBackClassTimes() {
        Course firstCourse = course("40101", "[{\"day\":1,\"start\":9.0,\"end\":10.0}]", "");
        Course nextCourse = course("40102", "[{\"day\":1,\"start\":10.0,\"end\":11.0}]", "");

        assertTrue(SelectedCourse.addSelectedCourse(firstCourse));
        assertTrue(SelectedCourse.addSelectedCourse(nextCourse));

        ArrayList<Course> selectedCourses = SelectedCourse.getSelectedCourses();
        assertEquals(2, selectedCourses.size());
        assertEquals("40101", selectedCourses.get(0).getCourse_number());
        assertEquals("40102", selectedCourses.get(1).getCourse_number());
    }

    @Test
    public void addSelectedCourse_acceptsCourseWithoutClassTime() {
        Course course = course("40101", "[]", "");

        assertTrue(SelectedCourse.addSelectedCourse(course));

        ArrayList<Course> selectedCourses = SelectedCourse.getSelectedCourses();
        assertEquals(1, selectedCourses.size());
        assertEquals(-1, selectedCourses.get(0).getClassDate());
    }

    @Test
    public void removeById_removesAllMeetingsForCourse() {
        Course course = course("40101", "[{\"day\":3,\"start\":10.0,\"end\":11.5},{\"day\":1,\"start\":10.0,\"end\":11.5}]", "");

        assertTrue(SelectedCourse.addSelectedCourse(course));
        SelectedCourse.removeById(Integer.parseInt("40101"));

        assertEquals(0, SelectedCourse.getSelectedCourses().size());
    }

    private static Course course(String courseNumber, String classTimes, String examTime) {
        return new Course(
                "",
                courseNumber,
                courseNumber,
                "Course " + courseNumber,
                3,
                30,
                "Instructor",
                classTimes,
                Integer.parseInt(courseNumber),
                examTime
        );
    }
}
