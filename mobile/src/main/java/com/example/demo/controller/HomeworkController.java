package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.Homework;
import com.example.demo.domain.User;
import com.example.demo.service.HomeworkService;

@Controller
@RequestMapping("/homework")
public class HomeworkController {
	@Autowired
	private HomeworkService service;
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public void add(@RequestBody Homework homework) {
		service.addHomework(homework.getPoster(), homework.getTitle(), 
				homework.getContent(), homework.getDeadline(), homework.getPostTime(), homework.getCourse());
	}
}
