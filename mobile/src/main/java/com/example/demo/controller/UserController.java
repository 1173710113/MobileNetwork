/**
 * 
 */
package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	@RequestMapping(value = "/login/{account}/{password}", method = RequestMethod.GET)
	@ResponseBody
	public User login(@PathVariable("account") String account, @PathVariable("password") String password) {
		System.out.println("123");
		User user = new User("1173710113", "1173710113", "学生", "滕文杰", "男", "");
		User us = userService.login(account,password);
		System.out.print(us.getId());
		return user;
	}
}
