/**
 * 
 */
package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.Course;

/**
 * @author msi-user
 *
 */
public interface CourseService {
	
	/**
	 * 
	 * @param course
	 */
	public void addCourse(Course course);
	/**
	 * 
	 * @param courseId
	 */
	public void deleteCourse(String courseId);
	/**
	 * 
	 * @param studentId
	 * @return
	 */
	public List<Course> queryCourseByStudentId(String studentId);
	/**
	 * 
	 * @param teacherId
	 * @return
	 */
	public List<Course> queryCourseByTeacherId(String teacherId);

}
