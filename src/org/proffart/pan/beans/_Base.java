package org.proffart.pan.beans;

import javax.servlet.http.HttpServletRequest;

public class _Base {
	protected HttpServletRequest request;
	
	public void setRequest(HttpServletRequest r) {
		request = r;
	}
}
