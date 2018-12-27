package tests.iota.home;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import framework.ui.entities.NavigationEntity;
import framework.ui.pageSteps.iota.IotaHomepageSteps;
import tests.BaseUITest;

public class HomeTest extends BaseUITest{

	WebDriver driver;
	IotaHomepageSteps iotaHomepageSteps = new IotaHomepageSteps();
	static String absolutePath = Paths.get("").toAbsolutePath().toString() + File.separator + "src" + File.separator
			+ "tests" + File.separator + "iota" + File.separator + "home" + File.separator + "resources" + File.separator;
	
	
	@Parameters({ "browser", "environment" })
	@BeforeClass(alwaysRun = true)
	public void prepareClassData(String browser, String environment) throws Exception {
	}

	@Parameters({ "browser", "environment" })
	@BeforeMethod(alwaysRun = true)
	public void prepareTestData(String browser, String environment) throws Exception {
		setupDriver(browser, environment);
	}

	@Test(dataProvider = "topNavData", enabled = true)
	public void topNavUIRedirectTest(NavigationEntity nav) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		iotaHomepageSteps.clickAndVerifyTopNavURLRedirect(softAssert, nav.getNavigation_name(), nav.getNavigation_url());
		softAssert.assertAll();
	}
	
	@Test(dataProvider = "mainNavTitleData", enabled = true)
	public void mainNavTitlesTest(NavigationEntity[] nav) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		iotaHomepageSteps.verifyMainNavigationOption(softAssert, NavigationEntity.getListOfNavigationNames(nav));
		softAssert.assertAll();
	}
	
	@Test(dataProvider = "mainNavData", enabled = true)
	public void mainNavContentTest(NavigationEntity nav) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		iotaHomepageSteps.clickAndVerifyMainNavContentOptions(softAssert, nav.getNavigation_name(), NavigationEntity.getListOfNavigationOptions(nav));
		softAssert.assertAll();
	}
	
	@Test(dataProvider = "mainNavData", enabled = true)
	public void mainNavRedirect(NavigationEntity nav) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		iotaHomepageSteps.clickAndVerifyMainNavOptionsRedirect(softAssert, nav.getNavigation_name(), nav.getNavigation_options());
		softAssert.assertAll();
	}
	
	@DataProvider(name = "topNavData", parallel = true)
	public static Object[][] topNavData() throws JsonParseException, JsonMappingException, IOException {
		NavigationEntity [] topNav = NavigationEntity.convertJsonFileToNavigation(new File(absolutePath + "topNav.json"));
		Object[][] dataObject = new Object[topNav.length][1];
		for(int i = 0; i < topNav.length; i++) {
			dataObject[i][0] = topNav[i];
		}
		return dataObject;
	}
	
	@DataProvider(name = "mainNavData", parallel = true)
	public static Object[][] mainNavData() throws JsonParseException, JsonMappingException, IOException {
		NavigationEntity [] mainNav = NavigationEntity.convertJsonFileToNavigation(new File(absolutePath + "mainNav.json"));
		Object[][] dataObject = new Object[mainNav.length][1];
		for(int i = 0; i < mainNav.length; i++) {
			dataObject[i][0] = mainNav[i];
		}
		return dataObject;
	}
	
	@DataProvider(name = "mainNavTitleData", parallel = true)
	public static Object[][] mainNavTitleData() throws JsonParseException, JsonMappingException, IOException {
		NavigationEntity [] mainNav = NavigationEntity.convertJsonFileToNavigation(new File(absolutePath + "mainNav.json"));
		Object[][] dataObject = new Object[1][1];
		dataObject[0][0] = mainNav;
		return dataObject;
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDownMethod() {
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() {
	}
	
	private void startTest() throws Exception {

	}

	private void endTest() {
	}
}
