package org.proffart.pan.fileManager;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;




/**
 * 
 * @author Karen S.
 * @version 1.0.0
 * copyright 360Sites ,Proffart team 2014
 * Class for getting path for files
 *
 */
public class PathManager {
	static Logger LOG = Logger.getLogger(PathManager.class);

	private static String PROPERTY_FILE="//properties//360Sites.properties";
	private static String USER_DATA_DIRECTORY;

	private static String PANORAMAS="panoramas//";
	private static String PICTURES="pictures//";
	private static String ICONS="icons//";
	private static String OTHER="other//";



	/**
	 * Initializing members
	 * 
	 */
	private static void initPathManager()
	{

		if(USER_DATA_DIRECTORY==null)
		{
			//Getting USER_DATA_DIRECTORY
			Properties prop=new Properties();
			try {
				prop.load(PathManager.class.getResourceAsStream(PROPERTY_FILE));
			} catch (IOException e) {
				LOG.error("Cannot find project property file", e);
			}	
			USER_DATA_DIRECTORY=prop.getProperty("USER_DATA_DIRECTORY");
		}

	}

	/**
	 * 
	 * @param directory
	 * @return state
	 * creating Directory if not exist
	 */
	public static boolean createDirectory(String directory)
	{

		File dir=new File(directory);
		if(!dir.exists())
		{
			return dir.mkdirs();
		}
		else
			return true;
	}

	/**
	 * 
	 * @param userID
	 * @return directory
	 * 	getting UserDirectory
	 */
	public static String getUserDirectory(int userID)
	{
		initPathManager();	
		String directory =USER_DATA_DIRECTORY+userID+"//";
		createDirectory(directory);		
		return directory;
	}

	/**
	 * 
	 * @param userID
	 * @param objectID
	 * @return directory
	 * Getting object directory
	 */
	public static String getObjectDirectory(int userID,int objectID)
	{
		initPathManager();	
		String directory=USER_DATA_DIRECTORY+userID+"//"+objectID+"//";
		createDirectory(directory);	
		return directory;	
	}

	/**
	 * 
	 * @param userID
	 * @param objectID
	 * @return directory
	 * 
	 * Getting panorama pictures directory
	 */
	public static String getPanoramaDirectory(int userID,int objectID)
	{
		return getObjectDirectory(userID, objectID)+PANORAMAS;
	}

	/**
	 * 
	 * @param userID
	 * @param objectID
	 * @return directory
	 * 
	 * Getting  pictures directory
	 */
	public static String getPicuresDirectory(int userID,int objectID)
	{
		return getObjectDirectory(userID, objectID)+PICTURES;
	}

	/**
	 * 
	 * @param userID
	 * @param objectID
	 * @return directory
	 * 
	 * Getting icons directory
	 */
	public static String getIconDirectory(int userID,int objectID)
	{
		return getObjectDirectory(userID, objectID)+ICONS;
	}

	/**
	 * 
	 * @param userID
	 * @param objectID
	 * @return directory
	 * 
	 * Getting other files directory
	 */
	public static String getOtherDirectory(int userID,int objectID)
	{
		return getObjectDirectory(userID, objectID)+OTHER;
	}
	
	/**
	 * 
	 * @param userID
	 * @param objectID
	 * 
	 * Creating all directories for object
	 */
	public static void createAllDirectories(int userID,int objectID)
	{
		getIconDirectory(userID, objectID);
		getOtherDirectory(userID, objectID);
		getPanoramaDirectory(userID, objectID);
		getPicuresDirectory(userID, objectID);
	}

}
