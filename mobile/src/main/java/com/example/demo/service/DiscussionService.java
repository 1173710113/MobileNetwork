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
	 * 
	 * @param posterId
	 * @return
	 */
	public List<Discussion> getPosterDiscussion(String posterId);
	
	/**
	 * 
	 * @param reply
	 */
	public void addReply(Reply reply);
	
	/**
	 * Get all the reply of the discussion.
	 * @param discussionId is the id of the discussion.
	 * @return
	 */
	public List<Reply> queryReplyByDiscussion(String discussionId);
	
	public Discussion queryDiscussionById(String id);
}
