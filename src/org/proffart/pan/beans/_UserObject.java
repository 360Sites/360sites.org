package org.proffart.pan.beans;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.proffart.pan.UserObject;

public class _UserObject extends _Base {
	private HashMap<String, String > fields = new HashMap<>();
	private int ID;
	
	public int createObject () throws ClassNotFoundException, IOException, SQLException	{
		UserObject instance = new UserObject(request);
		return (instance.createObject(fields));
	}
	
	public boolean editObject () throws ClassNotFoundException, IOException, SQLException	{
		UserObject instance = new UserObject(request);
		instance.editObject(fields);
		return true;
	}
	
	public boolean deleteObject () throws ClassNotFoundException, IOException, SQLException {
		UserObject instance = new UserObject(request);
		instance.deleteObject(ID);
		return true;
	}
	
	public List <HashMap <String, String>> getObjects() throws ClassNotFoundException, IOException, SQLException {
		UserObject instance = new UserObject(request);
		return (instance.getObjects());
	}

}
