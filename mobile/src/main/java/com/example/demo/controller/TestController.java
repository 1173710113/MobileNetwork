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
	public void addTest(@PathVariable("name") String name, @PathVariable("startTime") String startTime,
			@PathVariable("endTime") String endTime, @PathVariable("courseId") String courseId,
			@RequestBody List<Question> list) {
		Test test = new Test(null, name, startTime, endTime, courseId);
		service.addTest(test, list);
	}

	@RequestMapping("/gettest/{courseId}")
	@ResponseBody
	public List<Test> getTestList(@PathVariable("courseId") String courseId) {
		return service.getTestList(courseId);
	}

	@RequestMapping("/getquestion/{testId}")
	@ResponseBody
	public List<Question> getQuestionList(@PathVariable("testId") String testId) {
		return service.getQuestionList(testId);
	}

	@RequestMapping("/addscore")
	@ResponseBody
	public void addScore(@RequestBody Score score) {
		service.addScore(score.getTestId(), score.getStudentId(), score.getScore(), score.getEveryScore());
	}

	@RequestMapping(value = "/queryscore/{testId}/{studentId}", method = RequestMethod.GET)
	@ResponseBody
	public Score queryScore(@PathVariable("testId") String testId, @PathVariable("studentId") String studentId) {
		return service.queryScore(testId, studentId);
	}

	@RequestMapping("/getteststudent/{testId}")
	@ResponseBody
	public int getStudentNumber(@PathVariable("testId") String testId) {
		return service.getStudentList(testId).size();
	}

	@RequestMapping("/isjoin/{testId}/{studentId}")
	@ResponseBody
	public String isJoinTest(@PathVariable("testId") String testId, @PathVariable("studentId") String studentId) {
		if (service.queryScore(testId, studentId) == null) {
			return "false";
		}
		return "true";
	}

}