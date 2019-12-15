/**
 * 
 */
package com.example.demo.dao;

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

	/**
	 * 
	 * @param name
	 * @param teacherId
	 * @param maxVol
	 * @param destination
	 * @param startTime
	 * @param endTime
	 */
	public void addCourse(@Param("name") String name, @Param("teacher_id") String teacherId,
			@Param("max_vol") int maxVol, @Param("destination") String destination,
			@Param("start_time") String startTime, @Param("end_time") String endTime);

	/**
	 * 
	 * @param id is the id of course.
	 */
	public void deleteCourse(@Param("id") String id);

	/**
	 * 
	 * @param studentId
	 * @return
	 */
	public List<Course> queryCourseByStudentId(@Param("student_id") String studentId);

	/**
	 * 
	 * @param teacherId
	 * @return
	 */
	public List<Course> queryCourseByTeacherId(@Param("teacher_id") String teacherId);
}
