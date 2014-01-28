package org.proffart.pan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.proffart.pan.web.Notification;

/**
 * Notification code 1000 - 1100 
 * @author Ashot
 *
 */
public class User {
	
	private static HttpSession session;
	static Logger LOG = Logger.getLogger(DbManager.class);
	
	public static void setSession(HttpSession setSession) {
		session = setSession;
	}
	
	public static boolean isLogined() {
		boolean isLogined = false;
		if( !session.isNew() ) {
			if(session.getAttribute("isLogined") != null ){
				isLogined = (Boolean) session.getAttribute("isLogined");				
			}
		}
		return isLogined;
	}
	public static String getLang(){
		String lang = "en";
		if( !session.isNew() ) {
			if(session.getAttribute("lang") != null ){
				lang = (String) session.getAttribute("lang");				
			}
		}
		return lang;
	}
	public boolean login(String userName, String password) throws Exception {
		boolean isLogined = false;

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
					Notification.error(1000);
					//LOG.error("user is invalid");
				}
			}else{
				Notification.error(1001);
				//LOG.error("incorrect password");
			}
		}else{
			Notification.textSprintfArgs = new Object[1];
			Notification.textSprintfArgs[0] = userName;
			Notification.error(1002);
			//LOG.error("userName '" +userName+ "' does not exist");
		}
		session.setAttribute("isLogined", isLogined);
		return isLogined;
	}
}
