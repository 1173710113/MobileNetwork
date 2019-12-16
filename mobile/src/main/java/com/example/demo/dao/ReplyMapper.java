/**
 * 
 */
package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.Reply;

/**
 * @author msi-user
 *
 */
@Mapper
public interface ReplyMapper {

	/**
	 * 
	 * @param discussion
	 * @param posterId
	 * @param content
	 * @param time
	 */
	public void addReply(@Param("discussion")String discussion, @Param("poster_id")String posterId, @Param("content")String content, @Param("time")String time);
	
	/**
	 * 
	 * @param id
	 */
	public void deleteReply(@Param("id")String id);
	
	
	/**
	 * 
	 * @param discussion
	 * @return
	 */
	public List<Reply> queryReplyByDiscussion(@Param("discussion")String discussion);
}
