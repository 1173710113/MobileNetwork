package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.HomeworkMapper;

@Service
public class HomworkImp implements HomeworkService{
	@Autowired
	private HomeworkMapper mapper;
	@Override
	public void addHomework(String id, String title, String content) {
		// TODO Auto-generated method stub
		mapper.addHomework(id, "", title, content, "", "", "");
	}

}
