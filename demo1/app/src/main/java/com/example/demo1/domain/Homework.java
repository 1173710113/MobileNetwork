package com.example.demo1.domain;

import java.io.Serializable;

public class Homework implements Serializable {

    private String id;
    private String posterId;
    private String posterName;
    private String title;
    private String content;
    private String deadline;
    private String postTime;
    private String courseId;
    /**
     * @param id
     * @param posterId
     * @param posterName
     * @param title
     * @param content
     * @param deadline
     * @param postTime
     * @param courseId
     */
    public Homework(String id, String posterId, String posterName, String title, String content, String deadline,
                    String postTime, String courseId) {
        super();
        this.id = id;
        this.posterId = posterId;
        this.posterName = posterName;
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.postTime = postTime;
        this.courseId = courseId;
    }
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @return the posterId
     */
    public String getPosterId() {
        return posterId;
    }
    /**
     * @return the posterName
     */
    public String getPosterName() {
        return posterName;
    }
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }
    /**
     * @return the deadline
     */
    public String getDeadline() {
        return deadline;
    }
    /**
     * @return the postTime
     */
    public String getPostTime() {
        return postTime;
    }
    /**
     * @return the courseId
     */
    public String getCourseId() {
        return courseId;
    }
}
