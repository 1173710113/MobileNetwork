/**
 * 
 */
package com.example.demo.domain;

/**
 * @author msi-user
 *
 */
public class Homework {

	private String id;
	private String poster_id;
	private String title;
	private String content;
	private String deadline;
	private String postTime;
	private String course_id;
	
	/**
	 * @param id
	 * @param poster
	 * @param title
	 * @param content
	 * @param deadline
	 * @param postTime
	 * @param course
	 */
	public Homework(String id, String poster_id, String title, String content, String deadline, String postTime,
			String course_id) {
		super();
		this.id = id;
		this.poster_id = poster_id;
		this.title = title;
		this.content = content;
		this.deadline = deadline;
		this.postTime = postTime;
		this.course_id = course_id;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return the poster
	 */
	public String getPoster() {
		return poster_id;
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
	 * @return the course
	 */
	public String getCourse() {
		return course_id;
	}
}
