/**
 * 
 */
package com.example.demo.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.Course;

/**
 * @author msi-user
 *
 */
@Mapper
public interface CourseMapper {

	public void addCourse(Course course);
	
	public void enroll(String studentId, String courseId);

	public void deleteCourse(String id);
	
	public void dropCourse(String studentId, String courseId);
	
	public Course queryCourse(String id);

	public List<Course> queryCourseByStudentId(String studentId);

	public List<Course> queryCourseByTeacherId(String teacherId);

	public String queryCode(String code);

	public String queryCodeByCourse(@Param("course_id") String courseId);
	
	public void addCode(@Param("course_id")String courseId, @Param("code")String code, @Param("due_time")Date dueTime);
}
