/**
 * 
 */
package com.example.demo.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author msi-user
 *
 */
public interface JsonUtil {

	/**
	 * 
	 * @param request
	 * @return
	 */
	public String getJSONStringFromRequestBoday(HttpServletRequest request);
}
