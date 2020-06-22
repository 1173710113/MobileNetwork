package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.Question;
import com.example.demo.domain.Score;
import com.example.demo.domain.Test;
import com.example.demo.service.TestService;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	private TestService service;

	@RequestMapping("/addtest/{name}/{startTime}/{endTime}/{courseId}")
	@ResponseBody
	public void addTest(@RequestBody Test test, @RequestBody List<Question> list) {
		service.addTest(test, list);
	}

	@RequestMapping("/gettest/{courseId}")
	@ResponseBody
	public List<Test> getTestList(@PathVariable("courseId") String courseId) {
		return service.queryTestByCourse(courseId);
	}

	@RequestMapping("/getquestion/{testId}")
	@ResponseBody
	public List<Question> getQuestionList(@PathVariable("testId") String testId) {
		return service.getQuestionList(testId);
	}

	@RequestMapping("/addscore")
	@ResponseBody
	public void addScore(@RequestBody Score score) {
		service.addScore(score);
	}

	@RequestMapping(value = "/queryscore/{testId}/{studentId}", method = RequestMethod.GET)
	@ResponseBody
	public Score queryScore(@PathVariable("testId") String testId, @PathVariable("studentId") String studentId) {
		return service.queryScore(testId, studentId);
	}

	@RequestMapping("/isjoin/{testId}/{studentId}")
	@ResponseBody
	public String isJoinTest(@PathVariable("testId") String testId, @PathVariable("studentId") String studentId) {
		if (service.queryScore(testId, studentId) == null) {
			return "false";
		}
		return "true";
	}
	
	@RequestMapping("/getteststudent/{testId}")
	@ResponseBody
	public int getStudentNumber(@PathVariable("testId") String testId) {
		return service.getStudentList(testId);
	}
	
	@RequestMapping("/student/score")
	@ResponseBody
	public List<String> studentQueryScore(String courseId, String studentId) {
		return service.studentQueryScore(courseId, studentId);
	}
	

}