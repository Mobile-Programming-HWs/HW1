package com.sharif.terminator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SelectedCourse {
    private static ArrayList<Course> selectedCourses = new ArrayList<>();

    public enum AddCourseStatus {
        ADDED("درس موردنظر با موفقیت اضافه شد"),
        DUPLICATE_COURSE("این درس قبلا اضافه شده است"),
        EXAM_CONFLICT("زمان امتحان این درس با برنامه شما تداخل دارد"),
        CLASS_TIME_CONFLICT("زمان کلاس این درس با برنامه شما تداخل دارد");

        private final String message;

        AddCourseStatus(String message) {
            this.message = message;
        }

        public boolean isAdded() {
            return this == ADDED;
        }

        public String getMessage() {
            return message;
        }
    }

    public static ArrayList<Course> getSelectedCourses() {
        return new ArrayList<>(selectedCourses);
    }

    public static void clearSelectedCourses() {
        selectedCourses.clear();
    }

    public static boolean addSelectedCourse(Course course) {
        return addSelectedCourseWithStatus(course).isAdded();
    }

    public static AddCourseStatus addSelectedCourseWithStatus(Course course) {
        Course firstCourse = Course.getClone(course);
        Course secondCourse = Course.getClone(course);
        firstCourse.setDatePriority(1);
        secondCourse.setDatePriority(2);

        if (hasCourse(course.getCourse_number())) {
            return AddCourseStatus.DUPLICATE_COURSE;
        }
        if (hasExamConflict(course)) {
            return AddCourseStatus.EXAM_CONFLICT;
        }
        if (!firstCourse.hasClassTime()) {
            selectedCourses.add(firstCourse);
            sortArray();
            return AddCourseStatus.ADDED;
        }

        if (hasTimeConflict(firstCourse) || (secondCourse.hasSecondClassTime() && hasTimeConflict(secondCourse))) {
            return AddCourseStatus.CLASS_TIME_CONFLICT;
        }

        selectedCourses.add(firstCourse);
        if (secondCourse.hasSecondClassTime()) {
            selectedCourses.add(secondCourse);
        }
        sortArray();
        return AddCourseStatus.ADDED;
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

