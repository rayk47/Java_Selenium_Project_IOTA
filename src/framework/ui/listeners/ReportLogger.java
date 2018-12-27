package framework.ui.listeners;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.Reporter;

import framework.ui.seleniumConfiguration.DriverFactory;

public class ReportLogger {

	public static void logLastConsoleErrors() {
		WebDriver driver = DriverFactory.getInstance().getDriver();
		List<LogEntry> jsErrors = driver.manage().logs().get(LogType.BROWSER).getAll();
		if (!jsErrors.isEmpty()) {
			LogEntry lastError = jsErrors.get(jsErrors.size() - 1);
			Reporter.log("Timestamp: " + lastError.getTimestamp() + " Message: " + lastError.getMessage());
		}
	}
}
