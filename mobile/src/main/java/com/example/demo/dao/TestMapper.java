package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.Question;
import com.example.demo.domain.Score;
import com.example.demo.domain.Test;
import com.example.demo.domain.User;

@Mapper
public interface TestMapper {

	public void addTest(Test test);

	public List<Test> getTestList(@Param("courseId") String courseId);

	public void addQuestion(Question question);

	public List<Question> getQuestionList(@Param("testId") String testId);

	public Score queryScore(@Param("student_id") String studentId, @Param("test_id") String testId);

	public void addScore(@Param("test_id") String testId, @Param("student_id") String studentId,
			@Param("score") String score, @Param("everyScore") String everyScore);

	public List<String> getStudentList(@Param("testId") String testId);
	
	public List<String> studentQueryScore(String courseId, String studentId);
	
	public List<Score> queryScoreByTest(String testId);

}
