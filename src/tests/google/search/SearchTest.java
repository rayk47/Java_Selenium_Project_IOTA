package tests.google.search;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import framework.ui.helperClasses.UtilityMethods;
import framework.ui.pageSteps.google.GoogleHomepageSteps;
import framework.ui.pageSteps.google.GoogleSearchResultSteps;
import tests.BaseUITest;

public class SearchTest extends BaseUITest{

	WebDriver driver;
	GoogleHomepageSteps googleHomepageSteps = new GoogleHomepageSteps();
	GoogleSearchResultSteps googleSearchResultSteps = new GoogleSearchResultSteps();
	
	static String baseText = "I am searching please wait while I get results";
	static int dataTestSize = 15;
	
	@DataProvider(name = "searchData", parallel = true)
	public static Object[][] searchData() {
		Object[][] dataObject = new Object[dataTestSize][1];
		for(int i = 0; i < dataTestSize; i++) {
			dataObject[i][0] = baseText + UtilityMethods.getTimeNow();
		}
		return dataObject;
		
	}
	  
	@Parameters({ "browser", "environment" })
	@BeforeClass(alwaysRun = true)
	public void prepareClassData(String browser, String environment) throws Exception {
	}

	@Parameters({ "browser", "environment" })
	@BeforeMethod(alwaysRun = true)
	public void prepareTestData(String browser, String environment) throws Exception {
		setupDriver(browser, environment);
	}

	@Test(dataProvider = "searchData")
	public void searchHome(String data) throws Exception {
		googleHomepageSteps.searchGoogle(data);
	}

	@Test(dataProvider = "searchData")
	public void searchResults(String data) throws Exception {	
		SoftAssert softAssert = new SoftAssert();
		googleHomepageSteps.searchGoogle(data);
		googleSearchResultSteps.verifySearchInputFieldValue(softAssert, data);
		softAssert.assertAll();
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
