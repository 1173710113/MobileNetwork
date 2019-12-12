/**
 * 
 */
package com.example.demo.domain;

/**
 * @author msi-user
 *
 */
public class User {

	private String id;
	private String password;
	private String type;
	private String name;
	private String sex;
	private String iconPath;

	/**
	 * @param id
	 * @param password
	 * @param type
	 * @param name
	 * @param sex
	 * @param iconPath
	 */
//	public User(String id, String password) {
//		
//		this.id = id;
//		this.password = password;
//		
//	}

	public User(String id, String password, String type, String name, String sex, String iconPath) {
		super();
		this.id = id;
		this.password = password;
		this.type = type;
		this.name = name;
		this.sex = sex;
		this.iconPath = iconPath;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", type=" + type + ", name=" + name + ", sex=" + sex
				+ ", iconPath=" + iconPath + "]";
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @return the iconPath
	 */
	public String getIconPath() {
		return iconPath;
	}
}
