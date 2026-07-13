package com.sharif.terminator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SelectedCourse {
    private static ArrayList<Course> selectedCourses = new ArrayList<>();

    public static ArrayList<Course> getSelectedCourses() {
        return selectedCourses;
    }

    public static boolean addSelectedCourse(Course course) {
        Course firstCourse = Course.getClone(course);
        Course secondCourse = Course.getClone(course);
        firstCourse.setDatePriority(1);
        secondCourse.setDatePriority(2);

        if (hasCourse(course.getId())) {
            return false;
        }
        if (firstCourse.getClassDate() == -1) {
            selectedCourses.add(firstCourse);
            sortArray();
            return true;
        }

        if (hasTimeConflict(firstCourse) || hasTimeConflict(secondCourse)) {
            return false;
        }

        selectedCourses.add(firstCourse);
        selectedCourses.add(secondCourse);
        sortArray();
        return true;
    }

    private static boolean hasCourse(int id) {
        for (Course selectedCourse : selectedCourses) {
            if (selectedCourse.getId() == id) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasTimeConflict(Course course) {
        for (Course selectedCourse : selectedCourses) {
            if (course.getClassDate() == selectedCourse.getClassDate() && timesOverlap(course, selectedCourse)) {
                return true;
            }
        }
        return false;
    }

    private static boolean timesOverlap(Course firstCourse, Course secondCourse) {
        return firstCourse.getClassTimeBeginning() < secondCourse.getClassTimeEnding()
                && firstCourse.getClassTimeEnding() > secondCourse.getClassTimeBeginning();
    }

    public static Comparator<Course> comparator = new Comparator<Course>() {
        public int compare(Course s1, Course s2) {

            int rollno1 = s1.getClassDate() * 100 + Math.round(s1.getClassTimeBeginning());
            int rollno2 = s2.getClassDate() * 100 + Math.round(s2.getClassTimeBeginning());

            return rollno1 - rollno2;
        }
    };

    public static void sortArray() {
        Collections.sort(selectedCourses, SelectedCourse.comparator);
    }
}

