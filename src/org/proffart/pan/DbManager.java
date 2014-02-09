package org.proffart.pan;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
 *  This singleton class provides methods for connecting with DB,
 *  inserting data into Db and getting rows from DB   
 *
 */

public class DbManager {	
	
	private static Connection connection = null;
	static Logger LOG = Logger.getLogger(DbManager.class);
	private ResultSetMetaData rsmd;
	private static boolean isInit =false;
	private static DbManager DbManagerInstance;
	private static String dbPropertyFile="//properties//DBconfig.properties";
	
	/**
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	private DbManager() throws ClassNotFoundException, IOException, SQLException  {
		getConnection(); 
		isInit = true;
	}
	
	/**
	 * 
	 * @return instance of class (singleton pattern)
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	public static DbManager getInstance() throws ClassNotFoundException, IOException, SQLException {
		if(!isInit) {
			DbManagerInstance = new DbManager();
		}
		return DbManagerInstance;
	}
	
	/**
	 * *@return DB Connection 
	 * @category Database  	  
	 * @throws ClassNotFoundException 
	 * @throws IOException,  if fails reading properties
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws ClassNotFoundException, IOException, SQLException  {
		if( connection != null ) {
			return connection;
		}
		Properties DBprop=new Properties();
		DBprop.load(DbManager.class.getResourceAsStream(dbPropertyFile));
		Class.forName(DBprop.getProperty("MySqlDriver"));
		
		//Getting properties from file
		String DB_USERNAME = DBprop.getProperty("DBUserName");
		String DB_PASSWORD = DBprop.getProperty("DBUserPassword");
		String serverName  = DBprop.getProperty("ServerName");	
		String databaseName= DBprop.getProperty("DBName");
		
		//Database URL
		String DB_URL="jdbc:mysql://" + serverName + "/" + databaseName;	
		
		//Connecting to database
		connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		
		return connection;
	}
	
	/**
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String, String> getRow( String sql ) throws SQLException  {
		HashMap<String, String> map = new HashMap<String, String>();
		PreparedStatement pstmt = connection.prepareStatement( sql );
		ArrayList<HashMap<String, String>> list = executeAndGetHashMap(pstmt);
		if(list.size() == 1){
			map = list.get(0);
		}
		return map;
	}
	
	/**
	 * 
	 * @param pstmt
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String, String> getRow( PreparedStatement pstmt ) throws SQLException  {
		HashMap<String, String> map = new HashMap<String, String>();
		ArrayList<HashMap<String, String>> list = executeAndGetHashMap(pstmt);
		if(list.size() == 1){
			map = list.get(0);
		}
		return map;
	}
	
	/**
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<HashMap<String, String>> getRowSet( String sql ) throws SQLException  {
		PreparedStatement pstmt = connection.prepareStatement( sql );
		ArrayList<HashMap<String, String>> row = executeAndGetHashMap(pstmt);
		return row;
	}
	
	/**
	 * 
	 * @param pstmt
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<HashMap<String, String>> getRowSet( PreparedStatement pstmt  ) throws SQLException  {
		ArrayList<HashMap<String, String>> row = new ArrayList<>();
		row = executeAndGetHashMap(pstmt);
		return row;
	}
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String> getField() throws SQLException  {
		ArrayList<String> ret = new ArrayList<String>();
		for(int col = 1; col <= rsmd.getColumnCount(); col++) {
			String fildeName = rsmd.getColumnLabel(col);
			ret.add(fildeName);
		}
		return ret;
	}
	
	/**
	 * 
	 * @param tableName
	 * @param insert
	 * @throws SQLException
	 */
	public int insert( String tableName, HashMap<String, Object> insert ) throws SQLException {
		String str1 = "";
		String str2 = "";
		ArrayList<String> val = new ArrayList<String>();
		for( Map.Entry<String, Object> entry : insert.entrySet() ) {
			str1 += "`"+ entry.getKey() +"`, ";
			str2 += "?, ";
			val.add( entry.getValue().toString() );
		}
		str1 = str1.substring(0,str1.length()-2);
		str2 = str2.substring(0,str2.length()-2);
		String sql = "INSERT INTO `"+tableName+"` ("+str1+") VALUES ("+str2+")";
		PreparedStatement pstmt = connection.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
		for( int i=1; i<=val.size(); i++ ) {
			pstmt.setString(i, val.get(i-1));
		}
		int id = -1;
		pstmt.execute();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()){
			id = rs.getInt(1);
		}
		return id;
	}
	
	/**
	 * 
	 * @param pstmt
	 * @return
	 * @throws SQLException
	 */
	private ArrayList<HashMap<String, String>> executeAndGetHashMap( PreparedStatement pstmt ) throws SQLException {
		ArrayList<HashMap<String, String>> row = new ArrayList<HashMap<String, String>>();
		if(pstmt.execute()){
			ResultSet rs = pstmt.getResultSet();
			rsmd = rs.getMetaData();
			while (rs.next()) {
				HashMap<String, String> map = new HashMap<String, String>();
				for(int col = 1; col <= rsmd.getColumnCount(); col++) {
					String fildeName = rsmd.getColumnLabel(col);
					map.put(fildeName, rs.getString(col));
				}
				row.add(map);
			}
		}
		return row;
	}
	
	/**
	 * 
	 * @param pstmt
	 * @return
	 * @throws SQLException
	 */
	private ArrayList<ArrayList<String>> executeAndGetList( PreparedStatement pstmt ) throws SQLException  {
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
	}
}
