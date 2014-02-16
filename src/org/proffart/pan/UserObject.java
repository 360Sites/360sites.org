package org.proffart.pan;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.proffart.pan.fileManager.FileUpload.FileList;
import org.proffart.pan.fileManager.FileUpload.ObjectFile;

/**
 * 
 * @author Aram
 * class provides methods for creating, updating ,
 * deleting and getting  user's objects (places)
 */
public class UserObject extends Base{

	public UserObject(HttpServletRequest r) {
		super(r);
		
	}
	
	/**
	 * 
	 * @return list of objects of user
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	public List <HashMap <String, String>> getObjects () throws ClassNotFoundException, IOException, SQLException
	{
		String query = "SELECT * FROM object WHERE user_id = " + User.getID(request) + " AND del_status <> TRUE";
		DbManager instance = DbManager.getInstance();
		ArrayList <HashMap<String, String>> rowset = instance.getRowSet(query);
		return rowset;
	}
		
	/**
	 *  
	 * @param fields HashMap from request with key value pairs
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws ClassNotFoundException
	 * return id of created object 
	 */
	public int createObject ( HashMap <String, String> fields) throws ClassNotFoundException, IOException, SQLException
	{
		HashMap <String, Object> rows = new HashMap<String, Object>();
		rows.put("user_id", User.getID(request));
		rows.put("name", fields.get("name"));
		rows.put("description", fields.get("description"));
		rows.put("location", fields.get("location"));
		DbManager db = DbManager.getInstance();
		int id = db.insert("object", rows);
		return id;
	}
	
	/**
	 * 
	 * @param fields key value pairs for updating
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	public void editObject (HashMap<String, String> fields) throws ClassNotFoundException, IOException, SQLException
	{
		int id = Integer.parseInt(fields.get("id"));
		
		String query = "UPDATE object SET name = ? , description = ? , location = ? WHERE id = ? AND user_id = ? ";
		Connection connection = DbManager.getConnection();
		PreparedStatement pstmt = connection.prepareStatement( query );
		pstmt.setString(1, fields.get("name"));
		pstmt.setString(2, fields.get("description"));
		pstmt.setString(3, fields.get("location"));
		pstmt.setInt(4, id);
		pstmt.setInt(5, User.getID(request));
		pstmt.execute();
	}
	
	/**
	 * 
	 * @param id object id which must be deleted
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	public void deleteObject (int id) throws ClassNotFoundException, IOException, SQLException
	{
		String query = "UPDATE object SET del_status = TRUE , del_date = NOW() WHERE id = "+id+" AND user_id = "+User.getID(request);
		Connection con = DbManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.execute();
	}
	public String fileGetPath() {
		return "test/";
	}
	public FileList fileGetLst(int from, int limit, HashMap<String, Object> params) throws Exception  {
		String sql = "SELECT * FROM `file`";
		DbManager db = DbManager.getInstance();
		ArrayList<HashMap<String, String>> data = db.getRowSet(sql);
		ObjectFile[] files = new ObjectFile[data.size()];
		for( int i=0 ; i<data.size(); i++ ) {
			files[i] = new ObjectFile();
			files[i].name = data.get(i).get("name");
			files[i].id = Integer.parseInt(data.get(i).get("id"));
			files[i].size = Long.parseLong(data.get(i).get("size"));
			files[i].path = data.get(i).get("path");
			files[i].fileName = data.get(i).get("file_name");
			files[i].mimeType = data.get(i).get("mime_type");
		}
		FileList result = new FileList();
		result.files = files;
		return result;
	}
	public void fileAdd(ObjectFile file, HashMap<String, Object> params) {
		
	}
	public void fileDel(int fileID, HashMap<String, Object> params) {
		
	}
	

}
