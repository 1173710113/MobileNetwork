package com.example.demo1.domain;

public class TeacherCourse extends Course {

    private String code;

    /**
     * @param id
     * @param name
     * @param teacherId
     * @param teacherName
     * @param maxVol
     * @param destination
     * @param startTime
     * @param endTime
     * @param realVol
     * @param code
     */
    public TeacherCourse(String id, String name, String teacherId, String teacherName, int maxVol, String destination, String startTime, String endTime, int realVol, String code) {
        super(id, name, teacherId, teacherName, maxVol, destination, startTime, endTime, realVol);
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
