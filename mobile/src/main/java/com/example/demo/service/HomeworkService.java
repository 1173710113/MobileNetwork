package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.Homework;

public interface HomeworkService {
	
	public void addHomework(Homework homework);

	public List<Homework> init(String courseId);
}
