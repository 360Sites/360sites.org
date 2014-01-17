package org.proffart.pan;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;



/**
 * 
 *  * @author Karen S.,  Ashot M.
 *  * @version 1.0.1
 *    copyright 360Sites ,Proffart team 2013
 *     
 *
 */

public class DbManager {
	
	
	private static Connection connection;
	static Logger LOG = Logger.getLogger(DbManager.class);
	private ResultSetMetaData rsmd;
	private static boolean isInit =false;
	private static DbManager DbManagerInstance;
	
	
	
	private DbManager() {
		getConnection(); 
		isInit = true;
	}
	public static DbManager getInstance() {
		if(!isInit) {
			DbManagerInstance = new DbManager();
		}
		return DbManagerInstance;
	}
	
	/**
	 * *@return Connection 
	 * *@category Database  	  
	 */
	public static Connection getConnection()
	{
		if(isInit) {
			return connection;
		}
		Properties DBprop=new Properties();
		try {
			DBprop.load(new FileInputStream("properties/DBconfig.properties"));
		} catch (FileNotFoundException e) {
			
			LOG.error("Error connecting DB: Cannot find DB property file");			
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error("Error connecting DB: IO Exception");
			e.printStackTrace();
		}
		try {
			Class.forName(DBprop.getProperty("MySqlDriver"));
		} catch (ClassNotFoundException e) {
			LOG.error("Error connecting DB: Cannot find MysqlDriver");
			e.printStackTrace();
		}
		
		//Getting properties from file
		String DB_USERNAME = DBprop.getProperty("DBUserName");
		String DB_PASSWORD = DBprop.getProperty("DBUserPassword");
		String serverName  = DBprop.getProperty("ServerName");	
		String databaseName= DBprop.getProperty("DBName");
		
		//Database URL
		String DB_URL="jdbc:mysql://" + serverName + "/" + databaseName;	
		
		//Connecting to database
		try {
			connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		} catch (SQLException e) {
			LOG.error("Error connecting DB: SQL Error getting connection");
			e.printStackTrace();
		}
		
		return connection;
	}
	


	
	
	public HashMap<String, String> getRow( String sql ) throws Exception {
		try{
			HashMap<String, String> map = new HashMap<String, String>();
			PreparedStatement pstmt = connection.prepareStatement( sql );
			ArrayList<HashMap<String, String>> list = executeAndGetHashMap(pstmt);
			if(list.size() == 1){
				map = list.get(0);
			}
			return map;
		}catch( Exception e ){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	public HashMap<String, String> getRow( PreparedStatement pstmt ) throws Exception {
		try{
			HashMap<String, String> map = new HashMap<String, String>();
			ArrayList<HashMap<String, String>> list = executeAndGetHashMap(pstmt);
			if(list.size() == 1){
				map = list.get(0);
			}
			return map;
		}catch( Exception e ){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	public ArrayList<ArrayList<String>> getRowSet( String sql ) throws Exception {
		try{
			PreparedStatement pstmt = connection.prepareStatement( sql );
			ArrayList<ArrayList<String>> row = executeAndGetList(pstmt);
			return row;
		}catch( Exception e ){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	public ArrayList<ArrayList<String>> getRowSet( PreparedStatement pstmt  ) throws Exception {
		try{
			ArrayList<ArrayList<String>> row = new ArrayList<ArrayList<String>>();
			row = executeAndGetList(pstmt);
			return row;
		}catch( Exception e ){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	public ArrayList<String> getField() throws Exception{
		try{
			ArrayList<String> ret = new ArrayList<String>();
			for(int col = 1; col <= rsmd.getColumnCount(); col++) {
				String fildeName = rsmd.getColumnName(col);
				ret.add(fildeName);
			}
			return ret;
		}catch( Exception e ){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	public void insert( String tableName, HashMap<String, Object> insert ) throws Exception {
		String str1 = "";
		String str2 = "";
		ArrayList<String> val = new ArrayList<String>();
		for( Map.Entry<String, Object> entry : insert.entrySet() ) {
			str1 += "`"+ entry.getKey() +"`, ";
			str2 += "?, ";
			val.add( entry.getValue().toString() );
		}
		str1 = str1.substring(0,-2);
		str2 = str2.substring(0,-2);
		String sql = "INSERT INTO `"+tableName+"` ("+str1+") VALUES ("+str2+")";
		PreparedStatement pstmt = connection.prepareStatement( sql );
		for( int i=1; i<=val.size(); i++ ) {
			pstmt.setString(i, val.get(i-1));
		}
		if(!pstmt.execute()){
			throw new Exception("error in db");
		}
	}
	
	
	private ArrayList<HashMap<String, String>> executeAndGetHashMap( PreparedStatement pstmt ) throws Exception  {
		try{
			ArrayList<HashMap<String, String>> row = new ArrayList<HashMap<String, String>>();
			if(pstmt.execute()){
				ResultSet rs = pstmt.getResultSet();
				rsmd = rs.getMetaData();
				while (rs.next()) {
					HashMap<String, String> map = new HashMap<String, String>();
					for(int col = 1; col <= rsmd.getColumnCount(); col++) {
						String fildeName = rsmd.getColumnName(col);
						map.put(fildeName, rs.getString(col));
					}
					row.add(map);
				}
			}
			return row;
		}catch( Exception e ){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	private ArrayList<ArrayList<String>> executeAndGetList( PreparedStatement pstmt ) throws Exception  {
		try{
			ArrayList<ArrayList<String>> row = new ArrayList<ArrayList<String>>();
			if(pstmt.execute()){
				ResultSet rs = pstmt.getResultSet();
				rsmd = rs.getMetaData();
				while (rs.next()) {
					ArrayList<String> map = new ArrayList<String>();
					for(int col = 1; col <= rsmd.getColumnCount(); col++) {
						map.add(rs.getString(col));
					}
					row.add(map);
				}
			}
			return row;
		}catch( Exception e ){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
}
