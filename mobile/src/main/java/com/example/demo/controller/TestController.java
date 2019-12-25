package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

<<<<<<< HEAD
=======
import com.example.demo.domain.Homework;
import com.example.demo.domain.Question;
>>>>>>> ad86fc2da929f5cae5859c23468be29a591af0ca
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
<<<<<<< HEAD
	public List<Test> getTestLise(@PathVariable("courseId") String courseId) {
		return service.getTestList(courseId);
=======
	public List<Test> getTestList(@PathVariable("courseId") String courseId) {
		return service.getTestList(courseId);
	}
	@RequestMapping(value = "/addquestion", method = RequestMethod.POST)
	@ResponseBody
	public void addquestion(@RequestBody Question question) {
		service.addQuestion(question.getContent(), question.getAnswer(),
				question.getTestId(), question.getTestId());
	}
	@RequestMapping(value = "/gettest/{testId}", method = RequestMethod.GET)
	@ResponseBody
	public List<Question> getQuestionList(@PathVariable("testId") String testId) {
		return service.getQuestionList(testId);
>>>>>>> ad86fc2da929f5cae5859c23468be29a591af0ca
	}

}