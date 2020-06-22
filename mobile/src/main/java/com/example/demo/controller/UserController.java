/**
 * 
 */
package com.example.demo.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.User;
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

	@RequestMapping("/login")
	@ResponseBody
	public User login(@RequestBody User user) {
		User us = userService.login(user.getUserId(), user.getPassword());
		System.out.println("userlogin");
		return us;
	}
	
	@RequestMapping("/html/login")
	public String hLogin(String id, String password) {
		User user = userService.login(id, password);
		return "course";
	}

	@RequestMapping("/login/test")
	@ResponseBody
	public User login(String id, String password) {
		User us = userService.login(id, password);
		return us;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@RequestBody User user) {
		userService.register(user);
		return "success";
	}

	@RequestMapping("/updatepassword")
	public void updatePasssword(String id, String password) {
		userService.updateUserPassword(id, password);

	}

	@RequestMapping("/getrandomstudnet/{courseId}")
	public User getRandomUser(@PathVariable("courseId") String courseId) {
		List<User> studnets = userService.queryStudentByCourse(courseId);
		Random r = new Random();
		int n = r.nextInt(studnets.size());
		User student = studnets.get(n);
		return student;
	}

	@RequestMapping("/update/name")
	public void updateUserName(String id, String name) {
		userService.updateUserName(id, name);
	}

	@RequestMapping("update/password")
	public void updateUserPass(String id, String password) {
		userService.updateUserPassword(id, password);
	}
}
