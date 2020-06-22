/**
 * 
 */
package com.example.demo.domain;

import lombok.Data;

/**
 * @author msi-user
 *
 */
@Data
public class User {

	private String userId;
	private String password;
	private String type;
	private String name;
	private String sex;
	private String iconPath;
}
