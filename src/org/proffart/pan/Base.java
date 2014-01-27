package org.proffart.pan;

import javax.servlet.http.HttpServletRequest;

public class Base {
	protected HttpServletRequest request;
	
	public Base(HttpServletRequest r){
		request = r;
	}
}
