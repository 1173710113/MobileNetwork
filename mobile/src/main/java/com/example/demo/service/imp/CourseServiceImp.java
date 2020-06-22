/**
 * 
 */
package com.example.demo.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CourseMapper;
import com.example.demo.domain.Course;
import com.example.demo.service.CourseService;
import com.example.demo.util.CodeUtil;
import com.example.demo.util.ValidateUtil;

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
		String code = CodeUtil.createData();
		String result = courseMapper.queryCode(code);
		if(ValidateUtil.isEmpty(result)) {
			courseMapper.addCourse(course);
			courseMapper.addCode(course.getCourseId(), code, course.getStartTime());
		}
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
		return courseMapper.queryCourseByTeacherId(teacherId);
	}

	@Override
	public String enroll(String code, String studentId) {
		String courseId = courseMapper.queryCode(code);
		if(ValidateUtil.isEmpty(courseId)) {
			return "fail";
		}
		Course course = courseMapper.queryCourse(courseId);
		int realVol = course.getRealVol();
		int maxVol = course.getMaxVol();
		if(realVol >= maxVol) {
			return "fail";
		}
		courseMapper.enroll(studentId, courseId);
		return "success";
	}

	@Override
	public String dropCourse(String studentId, String courseId) {
		// TODO catch exception
		courseMapper.dropCourse(studentId, courseId);
		return "success";
	}

}
