/**
 * 
 */
package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.Discussion;
import com.example.demo.domain.Reply;

/**
 * @author msi-user
 *
 */
public interface DiscussionService {

	/**
	 * 
	 * @param discussion
	 * @param courseId
	 * @return
	 */
	public String addDiscussion(Discussion discussion);
	
	/**
	 * delete discussion where discussion_id=id.
	 * @param id
	 * @return
	 */
	public String deleteDiscussion(String id);
	
	/**
	 * get all the discussions related to the course.
	 * @param courseId
	 * @return
	 */
	public List<Discussion> getCourseDiscussion(String courseId);
	
	/**
	 * get all the discussions related to the course and the discussion's title contains string title.
	 * @param courseId
	 * @param title cannot be null.
	 * @return
	 */
	public List<Discussion> getCourseDiscussionByTitle(String courseId, String title);
	
	/**
	 * Reply a discussion.
	 * @param discussionId is the id of the discussion.
	 * @param uploaderId is the id of whom made the reply.
	 * @param content is the content of the reply and cannot be null or ''.
	 */
	public void addReply(String discussionId, String uploaderId, String content);
	
	/**
	 * Get all the reply of the discussion.
	 * @param discussionId is the id of the discussion.
	 * @return
	 */
	public List<Reply> queryReplyByDiscussion(String discussionId);
}
