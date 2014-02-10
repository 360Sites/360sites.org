package org.proffart.pan.beans;

import org.proffart.pan.User;

public class _User extends _Base {
	private User instance;
	/*-arguments-*/
	private String userName;
	private String password;
	private String language;
	/*-end-arguments-*/
	public void createInstance() {
		instance = new User(request);
	}

	public boolean login() throws Exception {
		return instance.login(userName, password);
	}

	public String getLanguage()
	{
		return language;
	}
	
}
