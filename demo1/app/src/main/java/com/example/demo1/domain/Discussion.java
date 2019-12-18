package com.example.demo1.domain;

import com.example.demo1.util.JSONUtil;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class Discussion extends DataSupport implements Serializable {

    private String discussionId;
    private String posterId;
    private String posterName;
    private String courseId;
    private String postTime;
    private String title;
    private String content;
    private int replyCount;

    /**
     * @param id
     * @param posterId
     * @param posterName
     * @param courseId
     * @param postTime
     * @param title
     * @param content
     * @param replyCount
     */
    public Discussion(String id, String posterId, String posterName, String courseId, String postTime, String title,
                      String content, int replyCount) {
        super();
        this.discussionId = id;
        this.posterId = posterId;
        this.posterName = posterName;
        this.courseId = courseId;
        this.postTime = postTime;
        this.title = title;
        this.content = content;
        this.replyCount = replyCount;
    }

    /**
     * @return the id
     */
    public String getId() {
        return discussionId;
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
     * @return the courseId
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * @return the postTime
     */
    public String getPostTime() {
        return postTime;
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
     * @return the replyCount
     */
    public int getReplyCount() {
        return replyCount;
    }

    @Override
    public String toString(){
        return JSONUtil.DiscussionParseJSON(this).toString();
    }

}