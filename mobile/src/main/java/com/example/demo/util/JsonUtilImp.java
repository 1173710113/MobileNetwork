/**
 * 
 */
package com.example.demo.util;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

/**
 * @author msi-user
 *
 */
public class JsonUtilImp implements JsonUtil {

	@Override
	public String getJSONStringFromRequestBoday(HttpServletRequest request) {
		StringBuffer json = new StringBuffer();
		String line = null;
		try{
			BufferedReader reader = request.getReader(); 
			while((line = reader.readLine()) != null){//一行一行的读数据
				json.append(line);
			}
		}catch(Exception e){
			System.out.println("Error reading JSON String " + e.toString());
		}
		return json.toString();//变为字符串返回

	}

}
