package org.proffart.pan;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public class UserObject extends Base{

	public UserObject(HttpServletRequest r) {
		super(r);
		
	}
		
	/**
	 * 
	 * @param r
	 * @param fields HashMap from request with key value pairs
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static int createObject (HttpServletRequest r, HashMap <String, Object> fields) throws ClassNotFoundException, IOException, SQLException
	{
		HashMap <String, Object> rows = new HashMap<>();
		rows.put("id", fields.get("id"));
		rows.put("user_id", fields.get(User.getId(r)));
		rows.put("name", fields.get("name"));
		rows.put("description", fields.get("descripton"));
		rows.put("location", fields.get("location"));
		rows.put("delete_day", null);
		//adding status field for deleted data, by default "NO"
		rows.put("isdeleted", false);
		DbManager db = DbManager.getInstance();
		int id = db.insert("object", rows);
		return id;		
	}
	
	/**
	 * 
	 * @param r
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void deleteObject (HttpServletRequest r, int id) throws ClassNotFoundException, IOException, SQLException
	{
		String query = "UPDATE object SET isdeleted = true AND delete_day = NOW() WHERE id = id AND user_id = 'User.getId(r)'";
		DbManager instance = DbManager.getInstance();
		Connection con = instance.getConnection();
		PreparedStatement pstmt = con.prepareStatement(query);
		ResultSet rst = pstmt.executeQuery(query);				
	}

}
