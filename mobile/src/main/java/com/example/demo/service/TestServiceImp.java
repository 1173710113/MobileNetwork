package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.TestMapper;
<<<<<<< HEAD
=======
import com.example.demo.dao.UserMapper;
import com.example.demo.domain.Question;
>>>>>>> ad86fc2da929f5cae5859c23468be29a591af0ca
import com.example.demo.domain.Test;

@Service
public class TestServiceImp implements TestService {
	@Autowired
	private TestMapper testMapper;

	@Override
	public void addTest(String name, String startTime, String endTime, String courseId) {
		testMapper.addTest(name, startTime, endTime, courseId);

	}

	@Override
	public List<Test> getTestList(String courseId) {
		return testMapper.getTestList(courseId);
	}

<<<<<<< HEAD
=======
	@Override
	public void addQuestion(String content, String answer, String testId, String score) {
		// TODO Auto-generated method stub
		testMapper.addQuestion(content, answer, testId, score);
	}

	@Override
	public List<Question> getQuestionList(String testId) {
		// TODO Auto-generated method stub
		return testMapper.getQuestionList(testId);
	}


>>>>>>> ad86fc2da929f5cae5859c23468be29a591af0ca
}
