package com.example.demo1.domain;

import org.litepal.crud.DataSupport;

public class Reply extends DataSupport {
    private String replyId;
    private String replyDiscussion;
    private String posterId;
    private String posterName;
    private String time;
    private String content;

    /**
     * @param id
     * @param replyDiscussion
     * @param posterId
     * @param posterName
     * @param time
     * @param content
     */
    public Reply(String id, String replyDiscussion, String posterId, String posterName, String time, String content) {
        super();
        this.replyId = id;
        this.replyDiscussion = replyDiscussion;
        this.posterId = posterId;
        this.posterName = posterName;
        this.time = time;
        this.content = content;
    }

    /**
     * @return the id
     */
    public String getId() {
        return replyId;
    }

    /**
     * @return the replyDiscussion
     */
    public String getReplyDiscussion() {
        return replyDiscussion;
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
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

}
