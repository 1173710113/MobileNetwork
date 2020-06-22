/**
 * 
 */
package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.Reply;

/**
 * @author msi-user
 *
 */
@Mapper
public interface ReplyMapper {

	public void addReply(Reply reply);

	public void deleteReply(String id);
	
	public List<Reply> queryReplyByDiscussion(String discussionId);
}
