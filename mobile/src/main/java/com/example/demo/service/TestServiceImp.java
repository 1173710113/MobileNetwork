package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.TestMapper;
import com.example.demo.domain.Question;
import com.example.demo.domain.Score;
import com.example.demo.domain.Test;
import com.example.demo.domain.TestToTeacher;
@Service
public class TestServiceImp implements TestService {
	@Autowired
	private TestMapper testMapper;
	
	@Override
	public void addTest(Test test, List<Question> questionList) {
		testMapper.addTest(test);
		String id = test.getId();
		for(int i = 0; i < questionList.size(); i++) {
			Question question = questionList.get(i);
			question.setTestId(id);
			testMapper.addQuestion(question);
		}
		
		
	}

	@Override
	public List<Test> getTestList(String courseId) {
		// TODO Auto-generated method stub
		return testMapper.getTestList(courseId);
	}

	@Override
	public List<Question> getQuestionList(String testId) {
		// TODO Auto-generated method stub
		return testMapper.getQuestionList(testId);
	}

	@Override
	public void addScore(String testId, String studentId, String score,String everyScore) {
		// TODO Auto-generated method stub
		testMapper.addScore(testId, studentId, score,everyScore);
	}

	@Override
	public Score queryScore(String testId, String studentId) {
		// TODO Auto-generated method stub
		return testMapper.queryScore(studentId, testId);
	}
	@Override
	public List<String> getStudentList(String testId) {
		// TODO Auto-generated method stub
		
		return testMapper.getStudentList(testId);
	}
	
	public List<String> studentQueryScore(String courseId, String studentId) {
		List<String> list = new ArrayList<>();
		list.addAll(testMapper.studentQueryScore(courseId, studentId));
		System.out.println(list.toString());
		return list;
	}
	
	public List<String> teacherQueryScore(String courseId) {
		List<String> list = new ArrayList<>();
		System.out.println(courseId);
		List<Test> testList = testMapper.getTestList(courseId);
		System.out.println(testList.size());
		for(Test test : testList) {
			List<Score> scoreList = testMapper.queryScoreByTest(test.getId());
			System.out.println(scoreList.size());
			if(scoreList.isEmpty()) {
				list.add("0");
			} else {
				int count = 0;
				for(Score score : scoreList) {
					count += Integer.parseInt(score.getScore());
				}
				System.out.println(count);
				float temp = Float.parseFloat(Integer.toString(count))/ scoreList.size();
				System.out.println(temp);
				list.add(Float.toString(temp));
				System.out.println(list.size());
			}
 		}
		return list;
	}

}
