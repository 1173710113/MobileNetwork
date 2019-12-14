package com.example.demo.domain;

public class Discussion {

	private String id;
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
		this.id = id;
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
	
}
