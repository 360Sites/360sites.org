package org.proffart.pan.beans;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.proffart.pan.UserObject;

public class _UserObject extends _Base {
	private UserObject instance = new UserObject(request);
	private HashMap<String, String > fields = new HashMap<>();
	
	
	public int createObject () throws ClassNotFoundException, IOException, SQLException
	{
		
		return (instance.createObject(fields));
	}
	
	public void editObject () throws ClassNotFoundException, IOException, SQLException
	{
		instance.editObject(fields);
	}
	
	public void deleteObject () throws ClassNotFoundException, IOException, SQLException
	{
		int id = Integer.parseInt(fields.get("id"));
		instance.deleteObject(id);
	}
	
	public List <HashMap <String, String>> getObjects() throws ClassNotFoundException, IOException, SQLException
	{
		return (instance.getObjects());
	}

}
