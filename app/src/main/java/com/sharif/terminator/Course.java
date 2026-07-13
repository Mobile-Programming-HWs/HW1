package com.sharif.terminator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class Course {
    private final String info;
    private final String course_id;
    private final String course_number;
    private final String name;
    private final int units;
    private final int capacity;
    private final String instructor;
    private final String class_times;
    private final int id;
    private final String exam_time;
    private final float classTimeBeginning;
    private final float classTimeEnding;
    private final int classFirstDate;
    private final int classSecondDate;
    private int datePriority;

    public Course(String info, String course_id, String course_number, String name, int units, int capacity, String instructor, String class_times, int id, String exam_time) {
        this.info = info;
        this.course_id = course_id;
        this.course_number = course_number;
        this.name = name;
        this.units = units;
        this.capacity = capacity;
        this.instructor = instructor;
        this.class_times = class_times;
        this.id = id;
        this.exam_time = exam_time;
        this.datePriority = 1;

        float start = -1;
        float end = -1;
        int firstDate = -1;
        int secondDate = -1;
        try {
            JSONArray classTimes = new JSONArray(class_times);
            if (classTimes.length() > 0) {
                JSONObject firstMeeting = classTimes.getJSONObject(0);
                start = (float) firstMeeting.getDouble("start");
                end = (float) firstMeeting.getDouble("end");
                firstDate = firstMeeting.getInt("day");
            }
            if (classTimes.length() > 1) {
                secondDate = classTimes.getJSONObject(1).getInt("day");
            }
        } catch (JSONException ignored) {
        }
        this.classTimeBeginning = start;
        this.classTimeEnding = end;
        this.classFirstDate = firstDate;
        this.classSecondDate = secondDate;
    }

    public String getInfo() {
        return info;
    }

    public String getCourse_id() {
        return course_id;
    }

    public String getCourse_number() {
        return course_number;
    }

    public String getName() {
        return name;
    }

    public int getUnits() {
        return units;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getClass_times() {
        return class_times;
    }

    public int getId() {
        return id;
    }

    public String getExam_time() {
        return exam_time;
    }

    public Float getClassTimeBeginning() {
        return classTimeBeginning;
    }

    public Float getClassTimeEnding() {
        return classTimeEnding;
    }

    public String getClassTimeBeginningText() {
        return formatClassTime(classTimeBeginning);
    }

    public String getClassTimeEndingText() {
        return formatClassTime(classTimeEnding);
    }

    public int getClassFirstDate() {
        return classFirstDate;
    }

    public int getClassSecondDate() {
        return classSecondDate;
    }

    public boolean hasClassTime() {
        return classFirstDate != -1;
    }

    public boolean hasSecondClassTime() {
        return classSecondDate != -1;
    }

    public void setDatePriority(int datePriority) {
        this.datePriority = datePriority;
    }

    public int getClassDate() {
        if (datePriority == 1) {
            return classFirstDate;
        }
        return classSecondDate;
    }

    private static String formatClassTime(float classTime) {
        if (classTime < 0) {
            return "";
        }
        int hour = (int) classTime;
        int minute = Math.round((classTime - hour) * 60);
        if (minute == 60) {
            hour += 1;
            minute = 0;
        }
        return String.format(Locale.US, "%02d:%02d", hour, minute);
    }
    
    public static Course getClone(Course course) {
        return new Course(course.info, course.course_id, course.course_number, course.name, course.units, course.capacity, course.instructor, course.class_times, course.id, course.exam_time);
    }
}
