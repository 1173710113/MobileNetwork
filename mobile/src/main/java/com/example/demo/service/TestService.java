package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.Test;

public interface TestService {
	public void addTest(String name,String startTime,String endTime,String courseId);
	public List<Test> getTestList(String courseId);
	
}
