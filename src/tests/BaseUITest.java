package tests;

import org.openqa.selenium.WebDriver;

import framework.ui.enums.BrowserType;
import framework.ui.seleniumConfiguration.DriverFactory;

public class BaseUITest {

	WebDriver driver;

	/*
	 * Setup driver
	 */
	public void setupDriver(String browser, String environment) throws Exception {
		DriverFactory.getInstance().setDriver(BrowserType.getBrowserEnum(browser));
		driver = DriverFactory.getInstance().getDriver();
		DriverFactory.getInstance().getDriver().get(environment);
	}

	private void startTest() throws Exception {

	}

	private void endTest() {
	}

}
