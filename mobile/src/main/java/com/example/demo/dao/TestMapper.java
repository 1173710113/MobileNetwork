package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.Question;
import com.example.demo.domain.Test;

@Mapper
public interface TestMapper {
	public void addTest(@Param("name") String name,
			@Param("startTime") String startTime, @Param("endTime") String endTime,@Param("courseId") String courseId);
	public List<Test> getTestList(@Param("courseId")String courseId);
	public void addQuestion(@Param("content") String content,@Param("answer") String answer
			,@Param("testId") String testId,@Param("score") String score);
	public List<Question> getQuestionList(@Param("testId") String testId);
}
