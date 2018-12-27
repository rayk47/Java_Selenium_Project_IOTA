package framework.ui.pages.google;

import org.openqa.selenium.By;

import framework.ui.pages.PageObject;

public class GoogleSearchResult extends PageObject{

	By searchInputLocator = By.cssSelector("input[title=\"Search\"]");
	By submitBtnLocator = By.cssSelector("button[value=\"Google Search\"]");
	
	public String getSearchText() {
		return getElementTxt(searchInputLocator);
	}
	
	public void clickSubmit() {
		click(submitBtnLocator);
	}
	
	public void sendKeysSearch(String searchText) {
		sendKeys(searchInputLocator, searchText);
	}
	
	public void clearKeysSearch() {
		clearKeys(searchInputLocator);
	}
	
	public boolean isSearchBarPresent() {
		return isElementPresent(searchInputLocator);
	}
}
