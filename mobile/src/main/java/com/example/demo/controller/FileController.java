/**
 * 
 */
package com.example.demo.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author msi-user
 *
 */

@Controller
@RequestMapping("/file")
public class FileController {
	@RequestMapping("/upload")
	@ResponseBody
	public String upload(MultipartFile file, HttpServletRequest request)throws IOException {
		String fileName = file.getOriginalFilename();
		System.out.println(fileName);
		String realPath = request.getSession().getServletContext().getRealPath("/upload");
		file.transferTo(new File(realPath, fileName));
		return "success";
	}
}
