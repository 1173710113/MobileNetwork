package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.Question;
import com.example.demo.domain.Score;
import com.example.demo.domain.Test;

public interface TestService {
	public void addTest(String name,String startTime,String endTime,String courseId);
	public List<Test> getTestList(String courseId);
	public void addQuestion(String content,String answer,String testId,String score);
	public List<Question> getQuestionList(String testId);
	public void addScore(String testId,String studentId,String score,String everyScore);
	public Score queryScore(String testId,String studentId);
	public List<String> getStudentList(String testId);
}
