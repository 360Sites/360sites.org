package org.proffart.pan;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;



/**
 * Servlet implementation class Gateway
 */
public class Gateway extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger LOG = Logger.getLogger(DbManager.class);   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Gateway() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest( request,  response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest( request,  response);
	}
	
	private void doRequest(HttpServletRequest request, HttpServletResponse response) {
		User.setSession(request.getSession(true));
		String json = "";
		AjaxResult ret = new AjaxResult();
		Gson gson = new Gson();
		
		String className = "org.proffart.pan." + request.getParameter("className");
		String methodName = request.getParameter("methodName");
		String argJsonString = request.getParameter("args");
		
		try{
			Object obj =  gson.fromJson(argJsonString, Class.forName(className));
			Method method = Class.forName(className).getMethod(methodName, new Class[] {});
			ret.result = method.invoke(obj, new Object[] {});
			ret.notifications = Notification.getNotifications();
			ret.status = true;
		}catch(Exception e){
			ret.status = false;
			ret.exception = e.getMessage();
		}
		try{
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			json = gson.toJson(ret);
			out.print(json);
		}catch(Exception e){
			//xz inch petqa anel vor inch vor ban tpi chgitem
		}
		free();
	}
	private void free(){
		Notification.free();
	}

}
