/**
 * 
 */
package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.User;

/**
 * @author msi-user
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@GetMapping("/login")
	@ResponseBody
	public User login(HttpServletRequest req, String account, String password) {
		System.out.println("123");
		User user = new User("1173710113", "1173710113", "学生", "滕文杰", "男", "");
		return user;
	}
}
