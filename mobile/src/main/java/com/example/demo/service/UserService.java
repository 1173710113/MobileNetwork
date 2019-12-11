/**
 * 
 */
package com.example.demo.service;

import com.example.demo.domain.User;

/**
 * @author msi-user
 *
 */
public interface UserService {

	/**
	 * 
	 * @param id
	 * @param password
	 * @param type
	 * @param name
	 * @param sex
	 * @return
	 */
	public String register(String id, String password, String type, String name, String sex);
	
	/**
	 * 
	 * @param id
	 * @param password
	 * @return
	 */
	public User login(String id, String password);
	
	/**
	 * 
	 * @param password should not be null or '' and should only contain valid character.
	 * @return
	 */
	public String updateUserPassword(String password);
	
	/**
	 * 
	 * @param name should not be null or ''.
	 * @return
	 */
	public String updateUserName(String name);
	
	/**
	 * 
	 * @param sex should be 男\女.
	 * @return
	 */
	public String updateUserSex(String sex);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public String deleteUser(String id);
}
