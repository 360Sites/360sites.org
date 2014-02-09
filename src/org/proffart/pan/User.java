package org.proffart.pan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.proffart.pan.web.Notification;

/**
 * Notification code 1000 - 1100 
 * @author Ashot
 *
 */
public class User extends Base{
	//private static HttpSession session;

	public User(HttpServletRequest r) {
		super(r);
	}
	
	public static int getID (HttpServletRequest r)
	{
		HttpSession session = r.getSession(true);
		int id = -1;
		try
		{			
			if (isLogined(r))
			{
				if (session.getAttribute("user_id") != null)
				{
					id = (int) session.getAttribute("user_id");
				}
				else
				{
					id = -1;
				}			
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return id;
	}
	
	
	/**
	 * Getting user's first Name
	 * @param r
	 * @return Name
	 */
	public static String  getName(HttpServletRequest r)
	{
		HttpSession session = r.getSession(true);
		String userName = null;
		try
		{			
			if (isLogined(r))
			{
				if (session.getAttribute("first_name") != null)
				{
					userName = session.getAttribute("first_name").toString();
				}
				else
				{
					userName = "Anonymous user";
				}			
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return userName;
	}
	
	public static boolean isLogined(HttpServletRequest r) {
		boolean isLogined = false;
		HttpSession session = r.getSession(true);
		if( !session.isNew() ) {
			if(session.getAttribute("isLogined") != null ){
				isLogined = (Boolean) session.getAttribute("isLogined");				
			}
		}
		return isLogined;
	}
	public static String getLang(HttpServletRequest r){
		String lang = "en";
		HttpSession session = r.getSession(true);
		if( !session.isNew() ) {
			if(session.getAttribute("lang") != null ){
				lang = (String) session.getAttribute("lang");				
			}
		}
		return lang;
	}
	public boolean login(String userName, String password) throws Exception {
		boolean isLogined = false;
		HttpSession session = request.getSession(true);
		DbManager db = DbManager.getInstance();
		Connection connection  = DbManager.getConnection();
		
		//generating sql query
		String sql = "SELECT *, MD5(?) AS external_password FROM `user` WHERE `user_name` = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, password);
		pstmt.setString(2, userName);
		HashMap<String, String> userInfo = db.getRow(pstmt);
		if( userInfo.get("id") != null ) { //ete tox ka uremn etenc user ka u id uni
			if( userInfo.get("password").equals(userInfo.get("external_password")) ) { //hamematum enq bazay u ekac passwordneri md5 hasery
				boolean isValidUser = (userInfo.get("is_valid_user").equals("1"));
				if( isValidUser ) { //stugum enq tvyal usery akti e te voch ete voch login cheq anum
					isLogined = true;
					
					
					//saving session information
					session.setAttribute("user_name", userInfo.get("user_name"));
					session.setAttribute("user_id", userInfo.get("id"));
					session.setAttribute("user_type_id", userInfo.get("user_type_id"));
					session.setAttribute("first_name", userInfo.get("first_name"));
					session.setAttribute("email", userInfo.get("email"));
					session.setAttribute("phone_number", userInfo.get("phone_number"));
					session.setAttribute("lang", "en"); //berel bazayic
				}else{
					Notification.error(request,1000);
				}
			}else{
				Notification.error(request,1001);
			}
		}else{
			Notification.textSprintfArgs = new Object[1];
			Notification.textSprintfArgs[0] = userName;
			Notification.error(request,1002);
		}
		session.setAttribute("isLogined", isLogined);
		return isLogined;
	}
}
