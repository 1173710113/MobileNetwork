package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.Homework;

public interface HomeworkService {
	public void addHomework(String user_id,String title,String content,String deadline,
			String postTime,String course_id);
	public List<Homework> init(String courseId);
}
