package org.proffart.pan.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;



/**
 * Servlet implementation class Gateway
 */
public class Gateway extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static Logger LOG = Logger.getLogger(DbManager.class);   
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
		String json = "";
		AjaxResult result = new AjaxResult();
		Gson gson = new Gson();
		
		String originalClassName = request.getParameter("className");
		String className = "org.proffart.pan.beans._" + originalClassName;
		String methodName = request.getParameter("methodName");
		String argJsonString = request.getParameter("args");
		
		try{
			if( methodName != "setRequest" && className != "org.proffart.pan.beans._Base" ){
				Object obj =  gson.fromJson(argJsonString, Class.forName(className));
				Method setRequest = Class.forName(className).getMethod("setRequest",HttpServletRequest.class);
				setRequest.invoke(obj, request);
				Method createInstance = Class.forName(className).getMethod("createInstance",new Class[] {});
				createInstance.invoke(obj, new Object[] {});
				Method method = Class.forName(className).getMethod(methodName, new Class[] {});
				result.result = method.invoke(obj, new Object[] {});
				result.notifications = Notification.getNotifications(request);
				result.status = true;
			}else{
				result.exception = "You must be joking";
			}
		}catch(Exception e){
			result.status = false;
			result.exception = "exception";
		}
		try{
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			json = gson.toJson(result);
			out.print(json);
		}catch(Exception e){
			//xz inch petqa anel vor inch vor ban tpi chgitem
		}
	}

}
