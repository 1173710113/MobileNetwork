package com.example.demo1.domain;

public class Test {
	protected String id;
	protected String name;
	protected String startTime;
	protected String endTime;
	protected String course_id;
	
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	public Test(String id, String name, String startTime, String endTime, String course_id) {
		super();
		this.id = id;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.course_id = course_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Override
	public String toString() {
		return "Test [id=" + id + ", name=" + name + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
	
}
