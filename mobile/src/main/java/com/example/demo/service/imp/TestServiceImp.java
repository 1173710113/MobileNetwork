package com.example.demo.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.TestMapper;
import com.example.demo.domain.Question;
import com.example.demo.domain.Score;
import com.example.demo.domain.Test;
import com.example.demo.service.TestService;
@Service
public class TestServiceImp implements TestService {
	@Autowired
	private TestMapper testMapper;
	
	@Override
	public void addTest(Test test, List<Question> questionList) {
		testMapper.addTest(test);
		String id = test.getTestId();
		for(int i = 0; i < questionList.size(); i++) {
			Question question = questionList.get(i);
			question.setTestId(id);
			testMapper.addQuestion(question);
		}
		
		
	}

	@Override
	public List<Test> queryTestByCourse(String courseId) {
		return testMapper.queryTestByCourse(courseId);
	}

	@Override
	public List<Question> getQuestionList(String testId) {
		return testMapper.getQuestionList(testId);
	}

	@Override
	public void addScore(Score score) {
		testMapper.addScore(score);
	}

	@Override
	public Score queryScore(String testId, String studentId) {
		return testMapper.queryScore(studentId, testId);
	}
	
	public List<String> studentQueryScore(String courseId, String studentId) {
		List<String> list = new ArrayList<>();
		list.addAll(testMapper.studentQueryScore(courseId, studentId));
		System.out.println(list.toString());
		return list;
	}

	@Override
	public Integer getStudentList(String testId) {
		return testMapper.queryScoreByTest(testId).size();
	}

}
