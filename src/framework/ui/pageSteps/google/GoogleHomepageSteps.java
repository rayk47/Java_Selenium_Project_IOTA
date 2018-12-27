package framework.ui.pageSteps.google;

import framework.ui.pages.google.GoogleHomepage;

public class GoogleHomepageSteps {

	GoogleHomepage homepage = new GoogleHomepage();
	
	public void searchGoogle(String searchText) {
		homepage.clearKeysSearch();
		homepage.sendKeysSearch(searchText);
		homepage.clickSubmit();
	}
}
