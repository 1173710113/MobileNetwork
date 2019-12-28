/**
 * 
 */
package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.XFile;
import com.example.demo.service.FileService;
import com.example.demo.util.TimeUtil;

/**
 * @author msi-user
 *
 */

@Controller
@RequestMapping("/file")
public class FileController {
	@Autowired
	FileService fileService;

	@RequestMapping("/upload")
	@ResponseBody
	public String upload(MultipartFile file, String posterId, String courseId, HttpServletRequest request) {
		String fileName = file.getOriginalFilename();
		String realPath = request.getSession().getServletContext().getRealPath("/upload/" + courseId);
		File targetFile = new File(realPath);
		// create folder if the directory is not exist
		if (!targetFile.exists() && !targetFile.isDirectory()) {
			targetFile.mkdirs();
		}
		File dir = new File(targetFile, fileName);
		try {
			file.transferTo(dir);
			long fileSize = dir.length();
			String postTime = TimeUtil.getTime();
			XFile temp = new XFile(null, realPath, fileName, fileSize, posterId, null, courseId, postTime);
			fileService.addFile(temp);
			return temp.getFileId();
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "fail";
	}

	@RequestMapping("/download/{fileName}/{courseId}")
	@ResponseBody
	public String download(@PathVariable("fileName") String fileName, @PathVariable("courseId") String courseId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String realPath = request.getSession().getServletContext().getRealPath("upload/" + courseId);
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

	@RequestMapping("/query/{courseId}")
	@ResponseBody
	public List<XFile> queryFileByCourse(@PathVariable("courseId") String courseId) {
		return fileService.queryFileByCourse(courseId);
	}
}
