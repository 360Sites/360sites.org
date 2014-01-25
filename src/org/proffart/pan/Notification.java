package org.proffart.pan;

public class Notification {
	private static Object[] notifications; 
	
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
	
	public static void addNotification(String type, String text, String title, Opts opts ){
		
	}
	
	
	
	public static Object[] getNotifications(){
		return notifications;
	}
}
