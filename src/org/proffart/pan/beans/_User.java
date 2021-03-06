package org.proffart.pan.beans;

import org.proffart.pan.User;

public class _User extends _Base {
	/*-arguments-*/
	private String userName;
	private String password;
	private String language;
	/*-end-arguments-*/
	
	public boolean login() throws Exception {
		User instance = new User(request);
		return instance.login(userName, password);
	}

	public String getLanguage()
	{
		return language;
	}
	
}
