package org.proffart.pan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


public class User {
	
	static HttpSession session;
	static Logger LOG = Logger.getLogger(DbManager.class);
	
	public static void setSession(HttpSession setSession) {
		session = setSession;
	}
	
	public static boolean isLogined() {
		boolean isLogined = false;
		if( !session.isNew() ) {
			isLogined = (Boolean) session.getAttribute("isLogined");
		}
		return isLogined;
	}
	
	public static boolean login(String userName, String password) {
		boolean isLogined = false;
		try {
			DbManager db = DbManager.getInstance();
			Connection connection  = DbManager.getConnection();
			String sql = "SELECT *, MD5(?) AS external_password FROM `user` WHERE `user_name` = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, password);
			pstmt.setString(2, userName);
			HashMap<String, String> userInfo = db.getRow(pstmt);
			if( userInfo.get("id") != null ) {
				if( userInfo.get("password").equals(userInfo.get("external_password")) ) {
					boolean isValidUser = (userInfo.get("is_valid_user").equals("1"));
					if( isValidUser ) {
						isLogined = true;
						session.setAttribute("user_name", userInfo.get("user_name"));
						session.setAttribute("user_id", userInfo.get("id"));
						session.setAttribute("user_type_id", userInfo.get("user_type_id"));
						session.setAttribute("first_name", userInfo.get("first_name"));
						session.setAttribute("email", userInfo.get("email"));
						session.setAttribute("phon_number", userInfo.get("phon_number"));
					}else{
						LOG.error("user is invalid");
					}
				}else{
					LOG.error("incorrect password");
				}
			}else{
				LOG.error("userName '"+userName+"' cannot exist");
			}
		} catch(SQLException e) {
			LOG.error("Error DB: "+e.getMessage());
			e.printStackTrace();
		} catch(Exception e) {
			LOG.error("Error DB: "+e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("isLogined", isLogined);
		return isLogined;
	}
}
