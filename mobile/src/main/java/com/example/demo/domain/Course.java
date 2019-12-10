/**
 * 
 */
package com.example.demo.domain;

/**
 * @author msi-user
 *
 */
public class Course {

	private String id;
	private String name;
	private User teacher;
	private int maxVol;
	private String destination;
	private String time;
	private int totalTime;
	private int realVol;
	
	/**
	 * @param id
	 * @param name
	 * @param teacher
	 * @param maxVol
	 * @param destination
	 * @param time
	 * @param totalTime
	 * @param realVol
	 */
	public Course(String id, String name, User teacher, int maxVol, String destination, String time, int totalTime,
			int realVol) {
		super();
		this.id = id;
		this.name = name;
		this.teacher = teacher;
		this.maxVol = maxVol;
		this.destination = destination;
		this.time = time;
		this.totalTime = totalTime;
		this.realVol = realVol;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the teacher
	 */
	public User getTeacher() {
		return teacher;
	}

	/**
	 * @return the maxVol
	 */
	public int getMaxVol() {
		return maxVol;
	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @return the totalTime
	 */
	public int getTotalTime() {
		return totalTime;
	}

	/**
	 * @return the realVol
	 */
	public int getRealVol() {
		return realVol;
	}
	
	
	
}
