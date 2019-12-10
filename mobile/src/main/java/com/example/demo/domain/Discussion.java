package com.example.demo.domain;

public class Discussion {

	private String id;
	private User poster;
	private String postTime;
	private String title;
	private String content;
	/**
	 * @param id
	 * @param poster
	 * @param postTime
	 * @param title
	 * @param content
	 */
	public Discussion(String id, User poster, String postTime, String title, String content) {
		super();
		this.id = id;
		this.poster = poster;
		this.postTime = postTime;
		this.title = title;
		this.content = content;
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
}
