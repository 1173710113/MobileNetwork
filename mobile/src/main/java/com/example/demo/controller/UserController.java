/**
 * 
 */
package com.example.demo.controller;

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

	@RequestMapping(value = "/login/{account}/{password}", method = RequestMethod.GET)
	@ResponseBody
	public User login(@PathVariable("account") String account, @PathVariable("password") String password) {
		User us = userService.login(account, password);
		return us;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@RequestBody User user) {
		System.out.println(user.toString());
		userService.register(user.getId(), user.getPassword(), user.getType(), user.getName(), user.getSex());
		return "success";
	}
	
	@RequestMapping("/updatepassword/{newPassword}")
	public void updatePasssword(@PathVariable("newPassword") String newPassword) {
		updateUserPassword(newPassword);
		return null;
	}
}
