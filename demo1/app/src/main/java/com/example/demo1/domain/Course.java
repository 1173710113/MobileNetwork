package com.example.demo1.domain;

import com.example.demo1.util.JSONUtil;

import org.litepal.crud.DataSupport;

public class Course extends DataSupport {
    private String courseId;
    private String name;
    private String teacherId;
    private String teacherName;
    private int maxVol;
    private String destination;
    private String startTime;
    private String endTime;
    private int realVol;

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
     */
    public Course(String id, String name, String teacherId, String teacherName, int maxVol, String destination,
                  String startTime, String endTime, int realVol) {
        super();
        this.courseId = id;
        this.name = name;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.maxVol = maxVol;
        this.destination = destination;
        this.startTime = startTime;
        this.endTime = endTime;
        this.realVol = realVol;
    }

    /**
     * @return the id
     */
    public String getId() {
        return courseId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the teacherId
     */
    public String getTeacherId() {
        return teacherId;
    }

    /**
     * @return the teacherName
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     * @return the maxVol
     */
    public int getMaxVol() {
        return maxVol;
    }

    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @return the realVol
     */
    public int getRealVol() {
        return realVol;
    }

    @Override
    public String toString() {
        return JSONUtil.CourseParseJSON(this).toString();
    }

}
