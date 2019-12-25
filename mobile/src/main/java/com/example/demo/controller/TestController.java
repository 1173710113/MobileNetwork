package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.Test;
import com.example.demo.service.TestService;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	private TestService service;

	@RequestMapping(value = "/addtest", method = RequestMethod.POST)
	@ResponseBody
	public void addTest(@RequestBody Test test) {
		service.addTest(test.getName(), test.getStartTime(), test.getEndTime(), test.getCourse_id());
	}

	@RequestMapping(value = "/gettest/{courseId}", method = RequestMethod.GET)
	@ResponseBody
	public List<Test> getTestLise(@PathVariable("courseId") String courseId) {
		return service.getTestList(courseId);
	}

}