package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.Discussion;
import com.example.demo.domain.Reply;
import com.example.demo.service.DiscussionService;

@Controller
@RequestMapping("/discussion")
public class DiscussionController {
	
	@Autowired
	DiscussionService discussionService;

	@PostMapping("/add")
	public void postDiscussion(@RequestBody Discussion discussion) {
		discussionService.addDiscussion(discussion);
	}
	
	@GetMapping("/querydiscussion/{courseId}")
	@ResponseBody
	public List<Discussion> queryDiscussion(@PathVariable("courseId")String courseId) {
		List<Discussion> discussionList = new ArrayList<>();
		discussionList.addAll(discussionService.getCourseDiscussion(courseId));
		return discussionList;
	}
	
	@GetMapping("/queryreply/{discussionId}")
	@ResponseBody
	public List<Reply> queryReply(@PathVariable("discussionId")String discussionId){
		List<Reply> replyList = new ArrayList<>();
		replyList.addAll(discussionService.queryReplyByDiscussion(discussionId));
		System.out.println(replyList.size());
		System.out.println(discussionId);
		return replyList;
	}
}
