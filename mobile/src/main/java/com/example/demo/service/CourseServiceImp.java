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
import com.example.demo.domain.TeacherCourse;
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
			courseMapper.addCode(course.getId(), code, course.getStartTime());
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
	public List<TeacherCourse> queryCourseByTeacherId(String teacherId) {
		List<Course> courseList = new ArrayList<>();
		List<TeacherCourse> teacherCourseList = new ArrayList<>();
		courseList.addAll(courseMapper.queryCourseByTeacherId(teacherId));
		for(int i = 0; i< courseList.size(); i++) {
			TeacherCourse course = new TeacherCourse(courseList.get(i));
			String code = courseMapper.queryCodeByCourse(courseList.get(i).getId());
			if(code == null) {
				code = "";
			}
			course.setCode(code);
			teacherCourseList.add(course);
		}
		return teacherCourseList;
	}

	@Override
	public List<String> getCourseStudent(String courseId) {
		
		return courseMapper.getStudentByCourse(courseId);
	}

	@Override
	public String enroll(String code, String studentId) {
		String course = courseMapper.queryCode(code);
		if(ValidateUtil.isEmpty(course)) {
			return "fail";
		}
		int real = courseMapper.queryCourseRealVol(course);
		int max = courseMapper.queryCourseMaxVol(course);
		if(real >= max) {
			return "fail";
		}
		courseMapper.enroll(studentId, course);
		courseMapper.updateCourseCountPlus(course);
		return "success";
	}

	@Override
	public String dropCourse(String studentId, String courseId) {
		String tempCourse = courseMapper.isStudentInCourse(studentId, courseId);
		if(ValidateUtil.isEmpty(tempCourse)) {
			return "fail";
		}
		if(courseMapper.queryCourseRealVol(courseId) < 1) {
			return "fail";
		}
		courseMapper.dropCourse(studentId, courseId);
		courseMapper.updateCourseCountMinus(courseId);
		return "success";
	}

}
