package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.TestMapper;
import com.example.demo.domain.Test;

@Service
public class TestServiceImp implements TestService {
	@Autowired
	private TestMapper testMapper;

	@Override
	public void addTest(String name, String startTime, String endTime, String courseId) {
		testMapper.addTest(name, startTime, endTime, courseId);

	}

	@Override
	public List<Test> getTestList(String courseId) {
		return testMapper.getTestList(courseId);
	}

}
