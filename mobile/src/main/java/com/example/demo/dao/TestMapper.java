package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.Question;
import com.example.demo.domain.Score;
import com.example.demo.domain.Test;

@Mapper
public interface TestMapper {

	public void addTest(Test test);

	public List<Test> getTestList(@Param("courseId") String courseId);

	public void addQuestion(Question question);

	public List<Question> getQuestionList(@Param("testId") String testId);

	public void addScore(@Param("testId") String testId, @Param("studentId") String studentId,
			@Param("score") String score);

	public Score queryScore(@Param("studentId") String studentId, @Param("testId") String testId);
	
	public List<String> queryTestComplited(String testId);
}
