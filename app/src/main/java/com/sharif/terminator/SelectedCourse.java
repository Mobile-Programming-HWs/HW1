package com.sharif.terminator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SelectedCourse {
    private static ArrayList<Course> selectedCourses = new ArrayList<>();

    public static ArrayList<Course> getSelectedCourses() {
        return new ArrayList<>(selectedCourses);
    }

    public static void clearSelectedCourses() {
        selectedCourses.clear();
    }

    public static boolean addSelectedCourse(Course course) {
        Course firstCourse = Course.getClone(course);
        Course secondCourse = Course.getClone(course);
        firstCourse.setDatePriority(1);
        secondCourse.setDatePriority(2);

        if (hasCourse(course.getCourse_number())) {
            return false;
        }
        if (hasExamConflict(course)) {
            return false;
        }
        if (!firstCourse.hasClassTime()) {
            selectedCourses.add(firstCourse);
            sortArray();
            return true;
        }

        if (hasTimeConflict(firstCourse) || (secondCourse.hasSecondClassTime() && hasTimeConflict(secondCourse))) {
            return false;
        }

        selectedCourses.add(firstCourse);
        if (secondCourse.hasSecondClassTime()) {
            selectedCourses.add(secondCourse);
        }
        sortArray();
        return true;
    }

    public static void removeById(int id) {
        for (int i = selectedCourses.size() - 1; i >= 0; i--) {
            if (selectedCourses.get(i).getId() == id) {
                selectedCourses.remove(i);
            }
        }
        sortArray();
    }

    private static boolean hasCourse(String courseNumber) {
        for (Course selectedCourse : selectedCourses) {
            if (selectedCourse.getCourse_number().equals(courseNumber)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasExamConflict(Course course) {
        String examTime = course.getExam_time().trim();
        if (examTime.isEmpty()) {
            return false;
        }
        for (Course selectedCourse : selectedCourses) {
            if (examTime.equals(selectedCourse.getExam_time().trim())) {
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
            int dateCompare = Integer.compare(s1.getClassDate(), s2.getClassDate());
            if (dateCompare != 0) {
                return dateCompare;
            }
            int startCompare = Float.compare(s1.getClassTimeBeginning(), s2.getClassTimeBeginning());
            if (startCompare != 0) {
                return startCompare;
            }
            return Float.compare(s1.getClassTimeEnding(), s2.getClassTimeEnding());
        }
    };

    public static void sortArray() {
        Collections.sort(selectedCourses, SelectedCourse.comparator);
    }
}

