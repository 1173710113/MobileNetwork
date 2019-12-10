package com.example.demo1.domain;

public class CourseClass {
    private String courseName;
    private String courseTeacher;
    private int classPeople;

    public CourseClass(String courseName, String courseTeacher, int classPeople) {
        this.courseName = courseName;
        this.courseTeacher = courseTeacher;
        this.classPeople = classPeople;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseTeacher(){
        return courseTeacher;
    }

    public int getClassPeople() {
        return classPeople;
    }
}
