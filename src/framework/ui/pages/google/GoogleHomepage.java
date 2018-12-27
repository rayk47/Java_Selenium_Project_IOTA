package framework.ui.pages.google;

import org.openqa.selenium.By;

import framework.ui.pages.PageObject;

public class GoogleHomepage extends PageObject{

	By searchInputLocator = By.cssSelector("input[aria-label=\"Search\"]");
	By submitBtnLocator = By.cssSelector("input[value=\"Google Search\"]");
	
	public void clickSubmit() {
		click(submitBtnLocator);
	}
	
	public void sendKeysSearch(String searchText) {
		sendKeys(searchInputLocator, searchText);
	}
	
	public void clearKeysSearch() {
		clearKeys(searchInputLocator);
	}
}
