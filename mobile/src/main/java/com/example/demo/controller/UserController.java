/**
 * 
 */
package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImp;
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
	public String login(@PathVariable("account") String account, @PathVariable("password") String password) {
//		User us = userService.login(account,password);
//		if(us!=null) {
//			return "success";
//		}
		return "fail";
	}
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public String add(@RequestBody User user) {
		System.out.println(user.toString());
		userService.register(user.getId(), user.getPassword(), user.getType(), user.getName(), user.getSex());
		
		return "success";
	}
}
