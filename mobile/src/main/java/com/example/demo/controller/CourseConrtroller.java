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

	@GetMapping("/add")
	public void addCourse(@RequestBody Course course) {
		courseService.addCourse(course);
	}

	@GetMapping("/query/student/{studentId}")
	@ResponseBody
	public List<Course> queryCourseByStudentId(@PathVariable("studentId") String studentId) {
		return courseService.queryCourseByStudentId(studentId);
	}
	
	@GetMapping("/query/teacher/{teacherId}")
	@ResponseBody
	public List<Course> queryCourseByTeacherId(@PathVariable("teacherId")String teacherId) {
		return courseService.queryCourseByTeacherId(teacherId);
	}
}
