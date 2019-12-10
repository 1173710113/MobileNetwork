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
	private User poster;
	private String title;
	private String content;
	private String deadline;
	private String postTime;
	private Course course;
	
	/**
	 * @param id
	 * @param poster
	 * @param title
	 * @param content
	 * @param deadline
	 * @param postTime
	 * @param course
	 */
	public Homework(String id, User poster, String title, String content, String deadline, String postTime,
			Course course) {
		super();
		this.id = id;
		this.poster = poster;
		this.title = title;
		this.content = content;
		this.deadline = deadline;
		this.postTime = postTime;
		this.course = course;
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
	public User getPoster() {
		return poster;
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
	public Course getCourse() {
		return course;
	}
}
