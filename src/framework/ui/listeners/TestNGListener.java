package framework.ui.listeners;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import framework.ui.seleniumConfiguration.DriverFactory;

public class TestNGListener extends TestListenerAdapter {

	
	@Override
	public void onTestFailure(ITestResult tr) {
		try {
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			if (isDriverValid()) {
				getDriverAndTakeScreenshot(tr);
				ReportLogger.logLastConsoleErrors();
				closeDriver();
			}
		} catch (Exception e) {
			// Using a try catch here as if anything happens in this listener then the test
			// goes missing in the report..which can cause hrs/days of investigation to
			// figure out why
			Reporter.log("Exception Thrown in on Test Failure Listener");
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		try {
			if (isDriverValid()) {
				closeDriver();
			}
		} catch (Exception e) {
			// Using a try catch here as if anything happens in this listener then the test
			// goes missing in the report..which can cause hrs/days of investigation to
			// figure out why
			Reporter.log("Exception Thrown in on Test Success Listener");
			e.printStackTrace();
		}
	}
	
	@Override
	public void onConfigurationFailure(ITestResult tr) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Reporter.log("<br>");
		Reporter.log(tr.getMethod().getRealClass() + " Configuration Failure:");
		Reporter.log("<br>");
		onTestFailure(tr);
	}
	
	private void getDriverAndTakeScreenshot(ITestResult tr) {
		try {
			WebDriver driver = DriverFactory.getInstance().getDriver();
			takeScreenShot(tr, driver);
		} catch (Exception e) {
			Reporter.log("<br>");
			Reporter.log("Screen Capture Failed : " + tr.getMethod().getMethodName());
			Reporter.log("<br>");
		}
	}

	private void takeScreenShot(ITestResult tr, WebDriver driver) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String NewFileNamePath = null;
		File directory = new File(".");
		Date date = new Date();
		String methodName = date.getTime() + tr.getMethod().getMethodName();
		try {
			NewFileNamePath = directory.getCanonicalPath() + File.separator + "target"
					+ File.separator + "surefire-reports" + File.separator + "html" + File.separator
					+ "Screenshots" + File.separator + methodName + ".png";
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		copyScreenShotFile(scrFile, NewFileNamePath);
		logScreenShot(date, methodName);
	}

	private void copyScreenShotFile(File scrFile, String NewFileNamePath) {
		try {
			FileUtils.copyFile(scrFile, new File(NewFileNamePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void logScreenShot(Date date, String methodName) {
		String reportFilePath = "." + File.separator + "Screenshots" + File.separator + methodName
				+ ".png";
		Reporter.log("<br>");
		Reporter.log("<p>" + methodName.replaceFirst("\\d+", "") + "</p>" + "<a href='"
				+ reportFilePath + "'>Click to open screenshot</a><img src=" + reportFilePath
				+ " height='350' width='700'>");
		Reporter.log("<br>");
		Reporter.log(date.toString());
		Reporter.log("<br>");
	}

	private void closeDriver() {
		WebDriver driver = DriverFactory.getInstance().getDriver();
		driver.quit();
	}
	
	private boolean isDriverValid() {
		WebDriver driver = DriverFactory.getInstance().getDriver();
		return (driver != null && !driver.toString().contains("(null)"));
	}

}