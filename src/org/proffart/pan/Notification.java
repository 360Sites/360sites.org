package org.proffart.pan;

import java.util.ArrayList;
import java.util.HashMap;

public class Notification {
	private static ArrayList<Notifications> notifications = new ArrayList<Notifications>();
	public static Object[] titleSprintfArgs = new Object[0];
	public static Object[] textSprintfArgs = new Object[0];
	
	public static class Opts {
		public boolean closeButton = true;
		public String positionClass = "toast-bottom-right";
		public int showDuration = 300;
		public int hideDuration = 1000;
		public int timeOut = 5000;
		public int extendedTimeOut = 1000;
		public String showEasing = "swing";
		public String hideEasing = "linear";
		public String showMethod = "fadeIn";
		public String hideMethod = "fadeOut";
	}
	protected static class Notifications {
		public String type;
		public String text;
		public String title;
		public Opts opts;
	}
	
	public static void addNotification(String type, String text, String title, Opts opts ){
		if(textSprintfArgs.length>0) {
			for( Object obj : textSprintfArgs )
				text = String.format(text, obj);
		}
		if(titleSprintfArgs.length>0) {
			for( Object obj : titleSprintfArgs )
				title = String.format(title, obj);
		}
		titleSprintfArgs = new Object[0];
		textSprintfArgs = new Object[0];
		
		Notifications not = new Notifications();
		not.type = type;
		not.text = text;
		not.title = title;
		not.opts = opts;
		notifications.add(not);
	}
	/* -error- */
	public static void error( String text, String title, Opts opts ) {
		if(!text.isEmpty())
			addNotification("error",text,title,opts);
	}
	public static void error( String text, String title ) {
		Opts opts = new Opts();
		error(text,title,opts);
	}
	public static void error( String text ) {
		error(text,"");
	}
	public static void error( int code, Opts opts ) {
		HashMap<String, String> res = getNotification(code);
		error(res.get("text"),res.get("title"),opts);
	}
	public static void error( int code ) {
		Opts opts = new Opts();
		error(code, opts);
	}
	/* -info- */
	public static void info( String text, String title, Opts opts ) {
		if(!text.isEmpty())
			addNotification("info",text,title,opts);
	}
	public static void info( String text, String title ) {
		Opts opts = new Opts();
		info(text,title,opts);
	}
	public static void info( String text ) {
		info(text,"");
	}
	public static void info( int code, Opts opts ) {
		HashMap<String, String> res = getNotification(code);
		info(res.get("text"),res.get("title"),opts);
	}
	public static void info( int code ) {
		Opts opts = new Opts();
		info(code, opts);
	}
	/* -warning- */
	public static void warning( String text, String title, Opts opts ) {
		if(!text.isEmpty())
			addNotification("warning",text,title,opts);
	}
	public static void warning( String text, String title ) {
		Opts opts = new Opts();
		warning(text,title,opts);
	}
	public static void warning( String text ) {
		warning(text,"");
	}
	public static void warning( int code, Opts opts ) {
		HashMap<String, String> res = getNotification(code);
		warning(res.get("text"),res.get("title"),opts);
	}
	public static void warning( int code ) {
		Opts opts = new Opts();
		warning(code, opts);
	}
	/* -success - */
	public static void success ( String text, String title, Opts opts ) {
		if(!text.isEmpty())
			addNotification("success ",text,title,opts);
	}
	public static void success ( String text, String title ) {
		Opts opts = new Opts();
		success (text,title,opts);
	}
	public static void success ( String text ) {
		success (text,"");
	}
	public static void success( int code, Opts opts ) {
		HashMap<String, String> res = getNotification(code);
		success(res.get("text"),res.get("title"),opts);
	}
	public static void success( int code ) {
		Opts opts = new Opts();
		success(code, opts);
	}
	
	private static HashMap<String, String> getNotification(int code) {
		HashMap<String, String> ret = new HashMap<String, String>();
		try{
			String lang = Global.getLang();
			DbManager db = DbManager.getInstance();
			String sql = "SELECT `text_"+lang+"` AS `text`, `title_"+lang+"` AS `title` FROM notification WHERE `code` = "+Integer.toString(code);
			ret = db.getRow(sql);
		}catch(Exception e){
			error(e.getMessage());
			ret.put("text", Integer.toString(code));
			ret.put("title",Integer.toString(code));
		}
		return ret;
	}
	
	public static Object[] getNotifications(){
		return notifications.toArray();
	}
}
