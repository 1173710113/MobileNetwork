/**
 * 
 */
package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author msi-user
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@GetMapping("/login")
	public String login(HttpServletRequest req, String account, String password) {
		System.out.println("123");
		return "success";
	}
}
