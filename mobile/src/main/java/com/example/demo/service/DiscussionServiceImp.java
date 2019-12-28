/**
 * 
 */
package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DiscussionMapper;
import com.example.demo.dao.ReplyMapper;
import com.example.demo.domain.Discussion;
import com.example.demo.domain.Reply;

/**
 * @author msi-user
 *
 */
@Service
public class DiscussionServiceImp implements DiscussionService {

	@Autowired
	private DiscussionMapper discussionMapper;
	@Autowired
	private ReplyMapper replyMapper;

	@Override
	public String addDiscussion(Discussion discussion) {
		discussionMapper.addDiscussion(discussion.getPosterId(), discussion.getPostTime(), discussion.getTitle(),
				discussion.getContent(), discussion.getCourseId());
		return null;
	}

	@Override
	public String deleteDiscussion(String id) {
		discussionMapper.deleteDiscussion(id);
		return null;
	}

	@Override
	public List<Discussion> getCourseDiscussion(String courseId) {
		List<Discussion> list = new ArrayList<>();
		list = discussionMapper.queryDiscussionByCourse(courseId);
		return list;
	}

	@Override
	public List<Discussion> getCourseDiscussionByTitle(String courseId, String title) {
		List<Discussion> list = new ArrayList<>();
		list = discussionMapper.queryDiscussionbyCourseAndTitle(courseId, title);
		return list;
	}

	@Override
	public List<Discussion> getPosterDiscussion(String posterId) {
		List<Discussion> list = new ArrayList<>();
		list = discussionMapper.queryDiscussionByPoster(posterId);
		return list;
	}

	@Override
	public void addReply(Reply reply) {
		replyMapper.addReply(reply.getReplyDiscussion(), reply.getPosterId(), reply.getContent(), reply.getTime());
		discussionMapper.updateDiscussionReplyCountPlus(reply.getReplyDiscussion());
	}

	@Override
	public List<Reply> queryReplyByDiscussion(String discussionId) {
		List<Reply> list = new ArrayList<>();
		list = replyMapper.queryReplyByDiscussion(discussionId);
		return list;
	}
	
	public Discussion queryDiscussionById(String id) {
		return discussionMapper.queryDiscussionById(id);
	}

}
