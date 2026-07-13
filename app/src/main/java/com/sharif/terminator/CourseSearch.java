package com.sharif.terminator;

import java.util.ArrayList;
import java.util.Locale;

public class CourseSearch {
    public static ArrayList<Course> filter(ArrayList<Course> courses, String query) {
        ArrayList<Course> filteredCourses = new ArrayList<>();
        if (courses == null) {
            return filteredCourses;
        }

        String normalizedQuery = normalize(query);
        if (normalizedQuery.isEmpty()) {
            filteredCourses.addAll(courses);
            return filteredCourses;
        }

        for (Course course : courses) {
            if (matches(course, normalizedQuery)) {
                filteredCourses.add(course);
            }
        }
        return filteredCourses;
    }

    private static boolean matches(Course course, String normalizedQuery) {
        return contains(course.getName(), normalizedQuery)
                || contains(course.getInstructor(), normalizedQuery)
                || contains(course.getCourse_number(), normalizedQuery)
                || contains(course.getCourse_id(), normalizedQuery)
                || contains(String.valueOf(course.getId()), normalizedQuery);
    }

    private static boolean contains(String value, String normalizedQuery) {
        return value != null && normalize(value).contains(normalizedQuery);
    }

    private static String normalize(String value) {
        if (value == null) {
            return "";
        }
        return value.trim().toLowerCase(Locale.ROOT);
    }
}
