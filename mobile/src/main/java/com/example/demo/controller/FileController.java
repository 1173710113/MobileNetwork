/**
 * 
 */
package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
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
	public String upload(MultipartFile file, String poster, HttpServletRequest request)throws IOException {
		String fileName = file.getOriginalFilename();
		System.out.println(fileName);
		System.out.println(poster);
		String realPath = request.getSession().getServletContext().getRealPath("/upload/" + poster);
		File targetFile = new File(realPath);
		//create folder if the directory is not exist
		if(!targetFile.exists() && !targetFile.isDirectory()) {
			targetFile.mkdirs();
		}
		File dir = new File(targetFile, fileName);
		file.transferTo(dir);
		return "success";
	}
	
	@RequestMapping("/download")
	@ResponseBody
	public String download(String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String realPath = request.getSession().getServletContext().getRealPath("upload");
		File file = new File(realPath, fileName);
		FileInputStream stream = new FileInputStream(file);
		String extendFileName = fileName.substring(fileName.lastIndexOf("."));
		response.setContentType(request.getSession().getServletContext().getMimeType(extendFileName));
		response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
		response.setContentLengthLong(file.length());
		ServletOutputStream out = response.getOutputStream();
		FileCopyUtils.copy(stream, out);
		return "success";
	}
}
