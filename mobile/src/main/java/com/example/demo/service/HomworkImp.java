package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.HomeworkMapper;
import com.example.demo.domain.Homework;

@Service
public class HomworkImp implements HomeworkService{
	@Autowired
	private HomeworkMapper mapper;
	@Override
	public void addHomework(String user_id, String title, String content,String deadline,
			String postTime,String courseId) {
		// TODO Auto-generated method stub
		mapper.addHomework(user_id, title, content, deadline, postTime,courseId);
	}
	@Override
	public List<Homework> init(String courseId) {
		// TODO Auto-generated method stub
		List<Homework> homeworks = mapper.queryHomework(courseId);
		return homeworks;
	}
	
}
