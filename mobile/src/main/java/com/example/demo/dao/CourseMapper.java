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
	 * @param id
	 * @param name
	 * @param teacher
	 * @param maxVol
	 * @param destination
	 * @param time
	 * @param totalTime
	 */
	public void addCourse(@Param("id") String id, @Param("name") String name, @Param("teacher") String teacher,
			@Param("max_vol") int maxVol, @Param("destination") String destination, @Param("time") String time,
			@Param("total_time") int totalTime);

	/**
	 * 
	 * @param id
	 */
	public void deleteCourse(@Param("id") String id);

	/**
	 * 
	 * @param id
	 * @param name
	 * @param teacher
	 * @param maxVol
	 * @param destination
	 * @param time
	 * @param realTime
	 * @param realVol
	 */
	public void updateCourse(@Param("id") String id, @Param("name") String name, @Param("teacher") String teacher,
			@Param("max_vol") int maxVol, @Param("destination") String destination, @Param("time") String time,
			@Param("real_time") int realTime, @Param("real_vol") int realVol);

	/**
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public List<Course> queryCourse(@Param("id") String id, @Param("name") String name);
}
