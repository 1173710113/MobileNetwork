package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.Question;
import com.example.demo.domain.Score;
import com.example.demo.domain.Test;

public interface TestService {
	public void addTest(Test test, List<Question> questions);
	public List<Test> queryTestByCourse(String courseId);
	public List<Question> getQuestionList(String testId);
	public void addScore(Score score);
	public Score queryScore(String testId,String studentId);
	public List<String> studentQueryScore(String courseId, String studentId);
	public Integer getStudentList(String testId);
}
