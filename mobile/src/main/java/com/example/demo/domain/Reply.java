/**
 * 
 */
package com.example.demo.domain;

/**
 * @author msi-user
 *
 */
public class Reply {

	private String replyDiscussion;
	private User poster;
	private String time;
	private String content;
	/**
	 * @param replyDiscussion
	 * @param poster
	 * @param time
	 * @param content
	 */
	public Reply(String replyDiscussion, User poster, String time, String content) {
		super();
		this.replyDiscussion = replyDiscussion;
		this.poster = poster;
		this.time = time;
		this.content = content;
	}
	/**
	 * @return the replyDiscussion
	 */
	public String getReplyDiscussion() {
		return replyDiscussion;
	}
	/**
	 * @return the poster
	 */
	public User getPoster() {
		return poster;
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
