package org.proffart.pan;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public int createObject ( HashMap <String, Object> fields) throws ClassNotFoundException, IOException, SQLException
	{
		HashMap <String, Object> rows = new HashMap<>();
		rows.put("id", fields.get("id"));
		rows.put("user_id", fields.get(User.getId(request)));
		rows.put("name", fields.get("name"));
		rows.put("description", fields.get("descripton"));
		rows.put("location", fields.get("location"));
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
	public void deleteObject (int id) throws ClassNotFoundException, IOException, SQLException
	{
		String query = "UPDATE object SET del_status = TRUE AND del_date = NOW() WHERE id = "+id+" AND user_id = "+User.getId(request);
		Connection con = DbManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement(query);
		ResultSet rst = pstmt.executeQuery(query);				
	}

}
