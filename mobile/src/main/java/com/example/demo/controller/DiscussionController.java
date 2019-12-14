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
import com.example.demo.service.DiscussionService;

@Controller
@RequestMapping("/discussion")
public class DiscussionController {
	
	@Autowired
	DiscussionService discussionService;

	@PostMapping("/add")
	public void postDiscussion(@RequestBody Discussion discussion) {
		System.out.println("123");
		discussionService.addDiscussion(discussion);
	}
	
	@GetMapping("/querydiscussion/{courseId}")
	@ResponseBody
	public List<Discussion> queryDiscussion(@PathVariable("courseId")String courseId) {
		System.out.println("456");
		List<Discussion> list = new ArrayList<>();
		list.addAll(discussionService.getCourseDiscussion(courseId));
		return list;
	}
}
