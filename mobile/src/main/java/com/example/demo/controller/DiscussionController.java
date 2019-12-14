package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
