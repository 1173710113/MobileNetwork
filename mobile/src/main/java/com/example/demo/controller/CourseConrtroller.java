/**
 * 
 */
package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.Course;
import com.example.demo.domain.TeacherCourse;
import com.example.demo.service.CourseService;

/**
 * @author msi-user
 *
 */
@Controller
@RequestMapping("/course")
public class CourseConrtroller {

	@Autowired
	CourseService courseService;

	@RequestMapping("/add")
	public void addCourse(@RequestBody Course course) {
		courseService.addCourse(course);
	}
	
	@RequestMapping("/delete/{courseId}") 
	public void deleteCourse(@PathVariable("courseId")String courseId){
		courseService.deleteCourse(courseId);
	}

	@GetMapping("/query/student/{studentId}")
	@ResponseBody
	public List<Course> queryCourseByStudentId(@PathVariable("studentId") String studentId) {
		return courseService.queryCourseByStudentId(studentId);
	}
	
	@GetMapping("/query/teacher/{teacherId}")
	@ResponseBody
	public List<TeacherCourse> queryCourseByTeacherId(@PathVariable("teacherId")String teacherId) {
		return courseService.queryCourseByTeacherId(teacherId);
	}
	
	@RequestMapping("/enroll/{code}/{studentId}")
	@ResponseBody
	public String enroll(@PathVariable("code")String code, @PathVariable("studentId")String studentId) {
		return courseService.enroll(code, studentId);
	}
	
	@RequestMapping("/drop/{studentId}/{courseId}")
	@ResponseBody
	public String drop(@PathVariable("studentId")String studentId, @PathVariable("courseId")String courseId) {
		String data = courseService.dropCourse(studentId, courseId);
		return data;
	}
}
