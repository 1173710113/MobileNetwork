package com.example.demo.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.HomeworkMapper;
import com.example.demo.domain.Homework;
import com.example.demo.service.HomeworkService;

@Service
public class HomeworkServiceImp implements HomeworkService{
	@Autowired
	private HomeworkMapper mapper;
	@Override
	public void addHomework(Homework homework) {
		mapper.addHomework(homework);
	}
	@Override
	public List<Homework> init(String courseId) {
		List<Homework> homeworks = mapper.queryHomeworkByCourse(courseId);
		return homeworks;
	}
	
}
