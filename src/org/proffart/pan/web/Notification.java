package org.proffart.pan.web;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.proffart.pan.DbManager;
import org.proffart.pan.User;

public class Notification {
	public static Object[] titleSprintfArgs = new Object[0];
	public static Object[] textSprintfArgs = new Object[0];
	
	public static class Opts {
		public boolean closeButton = true;
		public String positionClass = "toast-top-right";
		public String toastClass = ""; //black
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
	
	@SuppressWarnings("unchecked")
	public static void addNotification(HttpServletRequest r, String type, String text, String title, Opts opts ){
		ArrayList<Notifications> notifications;
		if( r.getAttribute("notifications") instanceof ArrayList ) {
			notifications = (ArrayList<Notifications>) r.getAttribute("notifications");
		}else{
			notifications = new ArrayList<Notifications>();
		}
		if(textSprintfArgs.length>0) {
			for( Object obj : textSprintfArgs )
				text = String.format(text, obj);
			titleSprintfArgs = new Object[0];
		}
		if(titleSprintfArgs.length>0) {
			for( Object obj : titleSprintfArgs )
				title = String.format(title, obj);
			textSprintfArgs = new Object[0];
		}
		
		Notifications not = new Notifications();
		not.type = type;
		not.text = text;
		not.title = title;
		not.opts = opts;
		notifications.add(not);
		r.setAttribute("notifications", notifications);
	}
	/* -error- */
	public static void error(HttpServletRequest r, String text, String title, Opts opts ) {
		if(!text.isEmpty())
			addNotification(r, "error",text,title,opts);
	}
	public static void error(HttpServletRequest r, String text, String title ) {
		Opts opts = new Opts();
		error(r, text,title,opts);
	}
	public static void error(HttpServletRequest r, String text ) {
		error(r, text,"");
	}
	public static void error(HttpServletRequest r, int code, Opts opts ) {
		HashMap<String, String> res = getNotification(r, code);
		error(r, res.get("text"),res.get("title"),opts);
	}
	public static void error(HttpServletRequest r, int code ) {
		Opts opts = new Opts();
		error(r, code, opts);
	}
	/* -info- */
	public static void info(HttpServletRequest r, String text, String title, Opts opts ) {
		if(!text.isEmpty())
			addNotification(r, "info",text,title,opts);
	}
	public static void info(HttpServletRequest r, String text, String title ) {
		Opts opts = new Opts();
		info(r, text,title,opts);
	}
	public static void info(HttpServletRequest r, String text ) {
		info(r, text,"");
	}
	public static void info(HttpServletRequest r, int code, Opts opts ) {
		HashMap<String, String> res = getNotification(r, code);
		info(r, res.get("text"),res.get("title"),opts);
	}
	public static void info(HttpServletRequest r, int code ) {
		Opts opts = new Opts();
		info(r, code, opts);
	}
	/* -warning- */
	public static void warning(HttpServletRequest r, String text, String title, Opts opts ) {
		if(!text.isEmpty())
			addNotification(r, "warning",text,title,opts);
	}
	public static void warning(HttpServletRequest r, String text, String title ) {
		Opts opts = new Opts();
		warning(r, text,title,opts);
	}
	public static void warning(HttpServletRequest r, String text ) {
		warning(r, text,"");
	}
	public static void warning(HttpServletRequest r, int code, Opts opts ) {
		HashMap<String, String> res = getNotification(r, code);
		warning(r, res.get("text"),res.get("title"),opts);
	}
	public static void warning(HttpServletRequest r, int code ) {
		Opts opts = new Opts();
		warning(r, code, opts);
	}
	/* -success - */
	public static void success(HttpServletRequest r, String text, String title, Opts opts ) {
		if(!text.isEmpty())
			addNotification(r, "success ",text,title,opts);
	}
	public static void success(HttpServletRequest r, String text, String title ) {
		Opts opts = new Opts();
		success(r, text,title,opts);
	}
	public static void success(HttpServletRequest r, String text ) {
		success(r, text,"");
	}
	public static void success(HttpServletRequest r, int code, Opts opts ) {
		HashMap<String, String> res = getNotification(r, code);
		success(r, res.get("text"),res.get("title"),opts);
	}
	public static void success(HttpServletRequest r, int code ) {
		Opts opts = new Opts();
		success(r, code, opts);
	}
	
	private static HashMap<String, String> getNotification(HttpServletRequest r, int code) {
		HashMap<String, String> ret = new HashMap<String, String>();
		try{
			String lang = User.getLang(r);
			DbManager db = DbManager.getInstance();
			String sql = "SELECT `text_"+lang+"` AS `text`, `title_"+lang+"` AS `title` FROM notification WHERE `code` = "+Integer.toString(code);
			ret = db.getRow(sql);
		}catch(Exception e){
			error(r, e.getMessage());
			ret.put("text", Integer.toString(code));
			ret.put("title",Integer.toString(code));
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static Object[] getNotifications(HttpServletRequest r){
		ArrayList<Notifications> notifications;
		if( r.getAttribute("notifications") instanceof ArrayList ) {
			notifications = (ArrayList<Notifications>) r.getAttribute("notifications");
		}else{
			notifications = new ArrayList<Notifications>();
		}
		return notifications.toArray();
	}
}
