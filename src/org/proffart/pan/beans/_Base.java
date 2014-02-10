package org.proffart.pan.beans;

import javax.servlet.http.HttpServletRequest;

public class _Base {
	protected HttpServletRequest request;
	protected Object instance;
	
	public void setRequest(HttpServletRequest r) {
		request = r;
	}
	
	public void createInstance() {}
}
