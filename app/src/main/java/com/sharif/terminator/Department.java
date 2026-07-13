package com.sharif.terminator;

import java.util.ArrayList;

public class Department {
    private static ArrayList<String> departmentNames = new ArrayList<>();

    private final int file_name;
    private final String department_name;
    private ArrayList<Course> courses = new ArrayList<>();

    public int getFile_name() {
        return file_name;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public static ArrayList<String> getDepartmentNames() {
        return departmentNames;
    }

    public static void clearDepartmentNames() {
        departmentNames.clear();
    }

    public Department(int file_name, String department_name) {
        this.file_name = file_name;
        this.department_name = department_name;
        departmentNames.add(department_name);
    }
}
