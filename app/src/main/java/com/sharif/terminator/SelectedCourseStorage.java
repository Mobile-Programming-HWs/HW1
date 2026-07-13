package com.sharif.terminator;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class SelectedCourseStorage {
    private static final String PREFERENCES_NAME = "selected_courses";
    private static final String KEY_SELECTED_COURSE_IDS = "selected_course_ids";

    public static void save(Context context) {
        if (context == null) {
            return;
        }

        LinkedHashSet<Integer> selectedCourseIds = new LinkedHashSet<>();
        for (Course selectedCourse : SelectedCourse.getSelectedCourses()) {
            selectedCourseIds.add(selectedCourse.getId());
        }

        preferences(context).edit()
                .putString(KEY_SELECTED_COURSE_IDS, joinCourseIds(selectedCourseIds))
                .apply();
    }

    public static void restore(Context context, ArrayList<Department> departments) {
        SelectedCourse.clearSelectedCourses();
        if (context == null || departments == null) {
            return;
        }

        String selectedCourseIds = preferences(context).getString(KEY_SELECTED_COURSE_IDS, "");
        if (selectedCourseIds == null || selectedCourseIds.trim().isEmpty()) {
            return;
        }

        for (String selectedCourseId : selectedCourseIds.split(",")) {
            Course course = findCourseById(departments, parseCourseId(selectedCourseId));
            if (course != null) {
                SelectedCourse.addSelectedCourse(course);
            }
        }
    }

    private static SharedPreferences preferences(Context context) {
        return context.getApplicationContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private static String joinCourseIds(LinkedHashSet<Integer> selectedCourseIds) {
        StringBuilder builder = new StringBuilder();
        for (Integer selectedCourseId : selectedCourseIds) {
            if (builder.length() > 0) {
                builder.append(',');
            }
            builder.append(selectedCourseId);
        }
        return builder.toString();
    }

    private static int parseCourseId(String selectedCourseId) {
        try {
            return Integer.parseInt(selectedCourseId.trim());
        } catch (NumberFormatException ignored) {
            return -1;
        }
    }

    private static Course findCourseById(ArrayList<Department> departments, int selectedCourseId) {
        if (selectedCourseId == -1) {
            return null;
        }
        for (Department department : departments) {
            if (department == null || department.getCourses() == null) {
                continue;
            }
            for (Course course : department.getCourses()) {
                if (course.getId() == selectedCourseId) {
                    return course;
                }
            }
        }
        return null;
    }
}
