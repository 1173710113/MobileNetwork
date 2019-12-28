/**
 * 
 */
package com.example.demo.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.User;
import com.example.demo.service.CourseService;
import com.example.demo.service.UserService;

/**
 * @author msi-user
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private CourseService courseService;

	@RequestMapping("/login")
	@ResponseBody
	public User login(@RequestParam(value="account",required=true) String account,
		    @RequestParam(value="password",required=true) String password) {
		
		User us = userService.login(account, password);
		return us;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@RequestBody User user){
		
		try {
			userService.register(user.getId(), user.getPassword(), user.getType(), user.getName(), user.getSex());
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("用户名已存在");
		}
		return "success";
	}

	@RequestMapping("/updatepassword")
	public void updatePasssword(@RequestParam(value="password",required=true) String password) {
		userService.updateUserPassword(password);

	}

	@RequestMapping("/getrandomstudnet/{courseId}")
	public User getRandomUser(@PathVariable("courseId") String courseId) {
		List<String> studnets = courseService.getCourseStudent(courseId);
		Random r = new Random();
		int n = r.nextInt(studnets.size());
		String student = studnets.get(n);
		return userService.getUserById(student);
	}
	
}
