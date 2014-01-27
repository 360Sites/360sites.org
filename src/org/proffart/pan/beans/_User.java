package org.proffart.pan.beans;

import org.proffart.pan.User;

public class _User {
	private User instance;
	
	/*-arguments-*/
	private String userName;
	private String password;
	/*-end-arguments-*/
	
	public _User() {
		instance = new User();
	}
	
	public boolean login() throws Exception {
		return instance.login(userName, password);
	}
	
	
}
