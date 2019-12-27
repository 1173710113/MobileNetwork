package com.example.demo.domain;

public class TestToTeacher extends Test{
	
	private int count;
	
	public TestToTeacher(String id, String name, String startTime, String endTime, String course_id) {
		super(id, name, startTime, endTime, course_id);
	}
	
	public TestToTeacher(Test test, int count) {
		super(test.id, test.name, test.startTime, test.endTime, test.course_id);
		this.count = count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public int getCount() {
		return count;
	}
}
