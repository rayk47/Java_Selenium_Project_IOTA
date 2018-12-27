package framework.ui.pageSteps.iota;

import java.util.List;

import org.testng.asserts.SoftAssert;

import framework.ui.entities.NavigationOptions;
import framework.ui.pages.iota.IotaHomepage;

public class IotaHomepageSteps {

	IotaHomepage iotaHomepage = new IotaHomepage();
	
	public void verifyTopNavigationOption(SoftAssert softAssert, List<String> expectedOptions) {
		softAssert.assertEquals(iotaHomepage.getTopNavOptions(), expectedOptions, " Incorrect top navigation options");
	}
	
	public void verifyMainNavigationOption(SoftAssert softAssert, List<String> expectedOptions) {
		softAssert.assertEquals(iotaHomepage.getMainNavOptions(), expectedOptions, " Incorrect main navigation options");
	}
	
	public void verifyMainNavigationContentOptions(SoftAssert softAssert, String mainNav, List<String> expectedOptions) {
		iotaHomepage.clickMainNavBtn(mainNav);
		softAssert.assertEquals(iotaHomepage.getMainNavContentOptions(), expectedOptions, " Incorrect main navigation content options for " + mainNav);
	}
	
	public void verifyMainNaviSocialMediaURLs(SoftAssert softAssert, String mainNav, List<String> expectedOptions) {
		iotaHomepage.clickMainNavBtn(mainNav);
		softAssert.assertEquals(iotaHomepage.getMainNavSocialIconURL(), expectedOptions, " Incorrect main navigation social media options Urls " + mainNav);
	}
	
	public void clickAndVerifyTopNavURLRedirect(SoftAssert softAssert, String topNav, String expectedUrl) {
		if(iotaHomepage.isTopMobileNavElementDisplayed()) {
			iotaHomepage.clickTopNavMobileBtn();
		}
		iotaHomepage.clickTopNavBtn(topNav);
		iotaHomepage.switchTabs(1);
		softAssert.assertEquals(iotaHomepage.getCurrentPageURL(), expectedUrl, " Incorrect URL redirect for " + topNav);
	}
	
	public void clickAndVerifyMainNavContentOptions(SoftAssert softAssert, String mainNav, List<String> expectedOptions) {
		iotaHomepage.clickMainNavBtn(mainNav);
		softAssert.assertEquals(iotaHomepage.getMainNavContentOptions(), expectedOptions, " Incorrect Main Navigation Options For " + mainNav);
	}
	
	public void clickAndVerifyMainNavOptionsRedirect(SoftAssert softAssert, String mainNav, NavigationOptions[] expectedOptions) {
		for(int i = 0; i < expectedOptions.length; i++) {
			iotaHomepage.clickMainNavBtn(mainNav);
			iotaHomepage.clickMainNavContentBtn(expectedOptions[i].getNavigation_option_name());
			softAssert.assertEquals(iotaHomepage.getCurrentPageURL(), expectedOptions[i].getNavigation_option_url(), " Incorrect Main Navigation Option Redirect " + expectedOptions[i].getNavigation_option_name());
		}
	}
	
	public void exitMainNavigation() {
		iotaHomepage.clickMainNavCloseLocator();
	}
}
