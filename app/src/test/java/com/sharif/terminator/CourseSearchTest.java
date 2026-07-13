package com.sharif.terminator;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CourseSearchTest {
    @Test
    public void filter_returnsAllCoursesForEmptyQuery() {
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(course("C-40101", "40101", "Algorithms", "Instructor One", 40101));
        courses.add(course("C-40102", "40102", "Physics", "Instructor Two", 40102));

        assertEquals(2, CourseSearch.filter(courses, "").size());
        assertEquals(2, CourseSearch.filter(courses, "   ").size());
        assertEquals(2, CourseSearch.filter(courses, null).size());
    }

    @Test
    public void filter_matchesNameInstructorCourseNumberAndCourseId() {
        ArrayList<Course> courses = new ArrayList<>();
        Course algorithms = course("C-40101", "40101", "Advanced Algorithms", "Dr Stone", 40101);
        Course physics = course("P-50101", "50101", "Physics", "Dr Feynman", 50101);
        courses.add(algorithms);
        courses.add(physics);

        assertOnlyCourse(algorithms, CourseSearch.filter(courses, "advanced"));
        assertOnlyCourse(algorithms, CourseSearch.filter(courses, "stone"));
        assertOnlyCourse(algorithms, CourseSearch.filter(courses, "40101"));
        assertOnlyCourse(physics, CourseSearch.filter(courses, "P-50101"));
        assertOnlyCourse(physics, CourseSearch.filter(courses, "50101"));
    }

    @Test
    public void filter_isCaseInsensitive() {
        ArrayList<Course> courses = new ArrayList<>();
        Course course = course("C-40101", "40101", "Advanced Algorithms", "Dr Stone", 40101);
        courses.add(course);

        assertOnlyCourse(course, CourseSearch.filter(courses, "ALGORITHMS"));
    }

    private static void assertOnlyCourse(Course expectedCourse, ArrayList<Course> courses) {
        assertEquals(1, courses.size());
        assertEquals(expectedCourse, courses.get(0));
    }

    private static Course course(String courseId, String courseNumber, String name, String instructor, int id) {
        return new Course(
                "",
                courseId,
                courseNumber,
                name,
                3,
                30,
                instructor,
                "[{\"day\":1,\"start\":9.0,\"end\":10.0}]",
                id,
                ""
        );
    }
}
