/**
 * 
 */
package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.Discussion;

/**
 * @author msi-user
 *
 */
@Mapper
public interface DiscussionMapper {

	public void addDiscussion(Discussion discussion);
	
	public void deleteDiscussion(String id);
	
	public void updateDiscussionTitle(String id, String title);

	public void updateDiscussionContent(String id, String content);
	
	public List<Discussion> queryDiscussionByCourse(String courseId);
	
	public List<Discussion> queryDiscussionbyCourseAndTitle(String courseId, String title);
	
	public List<Discussion> queryDiscussionByPoster(String posterId);
	
	public Discussion queryDiscussion(String id);
}
