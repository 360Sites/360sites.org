package org.proffart.pan;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;








public class DbMAnager {
	private static boolean isInit = false;
	private static DbMAnager DbMAnagerInstance;
	private static Connection connection;
	
	private static String DB_URL = "jdbc:mysql://localhost:3306/360sites";
	//?autoReconnect=true&useUnicode=true&characterEncoding=utf8
	private static String DB_USERNAME = "root";
	private static String DB_PASSWORD = "";
	
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
	public static String xz()  {
		return "sdsd";
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
			Vector<HashMap<String, String>> list = getRowSet(sql);
			if(list.size() == 1){
				map = list.get(0);
			}
			return map;
		}catch( Exception e ){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	public Vector<HashMap<String, String>> getRowSet( String sql ) throws Exception {
		Vector<HashMap<String, String>> row = new Vector<HashMap<String, String>>();
		try{
			PreparedStatement pstmt = connection.prepareStatement( sql );
			if(pstmt.execute()){
				ResultSet rs = pstmt.getResultSet();
				ResultSetMetaData rsmd = rs.getMetaData();
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
