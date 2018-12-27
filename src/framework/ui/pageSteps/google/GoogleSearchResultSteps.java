package framework.ui.pageSteps.google;

import org.testng.asserts.SoftAssert;

import framework.ui.pages.google.GoogleSearchResult;

public class GoogleSearchResultSteps {

	GoogleSearchResult searchResults = new GoogleSearchResult();
	
	public void verifySearchInputFieldValue(SoftAssert softAssert, String expectedValue) {
		if(searchResults.isSearchBarPresent()) {
			softAssert.assertEquals(searchResults.getSearchText(), expectedValue, " Invalid retaining of search value");
		} else {
			softAssert.assertEquals(searchResults.isSearchBarPresent(), true, " Good job google you found I'm a bot");
		}
	}
	
}
