/**
 * 
 */
package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.Discussion;

/**
 * @author msi-user
 *
 */
@Mapper
public interface DiscussionMapper {

	/**
	 * 
	 * @param user the id of poster, should be valid.
	 * @param title cannot be null or ''.
	 * @param content.
	 */
	public void addDiscussion(@Param("user")String user, @Param("title")String title, @Param("content")String content);
	
	/**
	 * delete discussion where discussion_id = id.
	 * @param id should not be null or ''.
	 */
	public void deleteDiscussion(@Param("id")String id);
	
	/**
	 * update discussion's title.
	 * @param id 
	 * @param title should not be null or ''.
	 */
	public void updateDiscussionTitle(@Param("id")String id, @Param("title")String title);
	
	/**
	 * update discussion's content.
	 * @param id
	 * @param content
	 */
	public void updateDiscussionContent(@Param("id")String id, @Param("cotent")String content);
	
	/**
	 * 
	 * @param courseId
	 * @return
	 */
	public List<Discussion> queryDiscussionByCourse(@Param("course_id")String courseId);
	
	/**
	 * query Discussion where course_id = courseId and title like title.
	 * @param courseId
	 * @param title cannot be null.
	 * @return
	 */
	public List<Discussion> queryDiscussionbyCourseAndTitle(@Param("course_id")String courseId, @Param("title")String title);
}
