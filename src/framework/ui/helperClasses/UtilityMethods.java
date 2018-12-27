package framework.ui.helperClasses;

import java.time.LocalDateTime;

public class UtilityMethods {

	public static String getOperatingSystem() {
		return (System.getProperty("os.name").toLowerCase().contains("mac") ? "mac" : "windows");
	}

	/***
	 * Example return format 2018-12-09T11:00:45.457
	 * 
	 * @return
	 */
	public static String getTimeNow() {
		return LocalDateTime.now().toString();
	}


}
