package org.proffart.pan;


import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;








public class DbMAnager {
	
	private static boolean isInit = false;
	private static DbMAnager DbMAnagerInstance;
	private static Connection connection;
	
	private static String DB_URL = "jdbc:mysql://localhost:3306/360sites";
	//?autoReconnect=true&useUnicode=true&characterEncoding=utf8
	private static String DB_USERNAME = "root";
	private static String DB_PASSWORD = "";
	
	private ResultSetMetaData rsmd;
	
	
	private DbMAnager() throws Exception {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			//getConfig();
			connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD); 
			
			isInit = true;
		}catch( SQLException e ){
			e.printStackTrace();
			throw new Exception();
		}
	}
	public static DbMAnager getInstance() throws Exception {
		try{
			if(!isInit) {
				DbMAnagerInstance = new DbMAnager();
			}
			return DbMAnagerInstance;
		}catch( Exception e ){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	public static Connection getConnection() throws Exception {
		try{
			if(!isInit) {
				DbMAnagerInstance = new DbMAnager();
			}
			return connection;
		}catch( Exception e ) {
			e.printStackTrace();
			throw new Exception(e);
		}
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
			str2 += "'?', ";
			val.add( entry.getValue().toString() );
		    //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
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
						//int type = rsmd.getColumnType(col);
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
						//String fildeName = rsmd.getColumnName(col);
						//int type = rsmd.getColumnType(col);
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
	/*private void getConfig() throws Exception {
		try{
			Properties prop = new Properties();
			
			prop.load(DbMAnager.class.getClassLoader().getResourceAsStream("config.properties"));
			DB_URL = prop.getProperty("db_url");
			DB_USERNAME = prop.getProperty("db_username");
			DB_PASSWORD = prop.getProperty("db_password");
			
		}catch( IOException e ){
			throw new Exception(e);
		}
	}*/
}
