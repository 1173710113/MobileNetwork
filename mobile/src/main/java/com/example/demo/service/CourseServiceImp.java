/**
 * 
 */
package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CourseMapper;
import com.example.demo.domain.Course;

/**
 * @author msi-user
 *
 */
@Service
public class CourseServiceImp implements CourseService {

	@Autowired
	CourseMapper courseMapper;

	@Override
	public void addCourse(Course course) {
		courseMapper.addCourse(course.getName(), course.getTeacherId(), course.getMaxVol(), course.getDestination(),
				course.getStartTime(), course.getEndTime());
	}

	@Override
	public void deleteCourse(String courseId) {
		courseMapper.deleteCourse(courseId);

	}

	@Override
	public List<Course> queryCourseByStudentId(String studentId) {
		List<Course> courseList = new ArrayList<>();
		courseList.addAll(courseMapper.queryCourseByStudentId(studentId));
		return courseList;
	}

	@Override
	public List<Course> queryCourseByTeacherId(String teacherId) {
		List<Course> courseList = new ArrayList<>();
		courseList.addAll(courseMapper.queryCourseByTeacherId(teacherId));
		return courseList;
	}

}
