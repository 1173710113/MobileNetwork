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
	private String teacherId;
	private String teacherName;
	private int maxVol;
	private String destination;
	private String startTime;
	private String endTime;
	// 总课时
	private int totalTime;
	private int realVol;

	/**
	 * @param id
	 * @param name
	 * @param teacherId
	 * @param teacherName
	 * @param maxVol
	 * @param destination
	 * @param startTime
	 * @param endTime
	 * @param totalTime
	 * @param realVol
	 */
	public Course(String id, String name, String teacherId, String teacherName, int maxVol, String destination,
			String startTime, String endTime, int totalTime, int realVol) {
		super();
		this.id = id;
		this.name = name;
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.maxVol = maxVol;
		this.destination = destination;
		this.startTime = startTime;
		this.endTime = endTime;
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
	 * @return the teacherId
	 */
	public String getTeacherId() {
		return teacherId;
	}

	/**
	 * @return the teacherName
	 */
	public String getTeacherName() {
		return teacherName;
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
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
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
