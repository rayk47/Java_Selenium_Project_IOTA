package framework.ui.seleniumConfiguration;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import framework.ui.enums.BrowserType;
import framework.ui.helperClasses.UtilityMethods;

public class DriverFactory {

	private static DriverFactory instance = new DriverFactory();

	private DriverFactory() {
	}

	public static DriverFactory getInstance() {
		return instance;
	}

	ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>() {
		@Override
		protected WebDriver initialValue() {
			return null;
		}
	};

	// call this method to get the driver object and launch the browser
	public WebDriver getDriver() {
		return driver.get();
	}

	public WebDriver setDriver(BrowserType browser) throws Exception {
		if (browser.equals(BrowserType.CHROME) || browser.equals(BrowserType.HEADLESS)) {
			ChromeOptions chromeOptions = buildChromeOptions(browser);
			driver.set(new ChromeDriver(chromeOptions));
		} else {
			throw new Exception("Can not configure browser type");
		}
		return driver.get();
	}

	public void removeDriver() {// Quits the driver and closes the browser
		driver.get().quit();
		driver.remove();
	}

	private ChromeOptions buildChromeOptions(BrowserType browser) {
		String os = UtilityMethods.getOperatingSystem();
		String pathToDriver = setPathToDriverExe();
		setDriverExeFile(os, pathToDriver);
		Map<String, Object> preferences = buildChromePreferences();
		ChromeOptions chromeOptions = null;
		if (browser.equals(BrowserType.CHROME)) {
			chromeOptions = buildChromeOptions(preferences);
		} else if (browser.equals(BrowserType.HEADLESS)) {
			chromeOptions = buildHeadlessChromeOptions(preferences);
		}
		return chromeOptions;
	}

	private Map<String, Object> buildChromePreferences() {
		Map<String, Object> preferences = new Hashtable<String, Object>();
		preferences.put("profile.default_content_settings.popups", 0);
		return preferences;
	}

	private ChromeOptions buildChromeOptions(Map<String, Object> preferences) {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("prefs", preferences);
		chromeOptions.addArguments("--start-maximized");
		chromeOptions.addArguments("--ignore-certificate-errors");
		chromeOptions.addArguments("--disable-popup-blocking");
		chromeOptions.addArguments("--incognito");
		chromeOptions.addArguments("--window-size=1024x768");
		return chromeOptions;
	}

	private ChromeOptions buildHeadlessChromeOptions(Map<String, Object> preferences) {
		ChromeOptions chromeOptions = buildChromeOptions(preferences);
		chromeOptions.addArguments("--headless");
		return chromeOptions;
	}

	private String setPathToDriverExe() {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		String pathToDriver = s + File.separator + "drivers" + File.separator;
		return pathToDriver;
	}

	private void setDriverExeFile(String osName, String pathToDriver) {
		if (osName.equals("windows")) {
			System.setProperty("webdriver.chrome.driver", pathToDriver + "chromedriver_v2.41.exe");
		} else {
			System.setProperty("webdriver.chrome.driver", pathToDriver + "chromedriver_v2_41");
		}
	}

}
