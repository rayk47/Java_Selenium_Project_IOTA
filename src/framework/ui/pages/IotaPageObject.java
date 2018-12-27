package framework.ui.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class IotaPageObject extends PageObject{

	private By topNavBtn = By.cssSelector("a[class=\"top-menu-nav-link d-flex flex-column justify-content-center \"]");
	private By topMobileNavBtn = By.cssSelector("a[class=\"mobile-nav-open-icon d-flex flex-column justify-content-between align-items-end align-self-center\"]");
	private By mainNavBtn= By.cssSelector("span[class=\" main-nav-item d-inline-flex justify-content-between align-items-end\"]");
	private By mainNavContentOptions = By.cssSelector("a[class=\"visible main-nav-content-link align-self-start\"]");
	private By mainNavCloseIcon = By.cssSelector("a[class=\"nav-close-icon d-flex flex-column justify-content-between align-items-end align-self-center\"]");
	private By mainNavSocialIcon = By.cssSelector("div[class=\"nav-social d-flex justify-content-center justify-content-lg-start\"]");
	private By footerGroupContainer = By.cssSelector("div[class=\"footer-group\"]");
	private By footerGroupTitle = By.cssSelector("//div[starts-with(@class,\"footer-group-title\")]");
	private By footerGroupLinks = By.cssSelector("//div[starts-with(@class,\"footer-group-link\")]");
	
	public List<String> getTopNavOptions() {
		return getStringsFromElements(topNavBtn);
	}
	
	public List<String> getMainNavOptions() {
		return getStringsFromElements(mainNavBtn);
	}
	
	public List<String> getMainNavContentOptions() {
		return getStringsFromElements(mainNavContentOptions);
	}
	
	public List<String> getMainNavSocialIconURL(){
		return getAttributeFromElements(mainNavSocialIcon, "href");
	}
	
	public List<String> getFooterTitles() {
		return getStringsFromElements(footerGroupTitle);
	}
	
	public List<String> getFooterTitleOptions(String title) {
		WebElement footer = findElements(footerGroupContainer).stream().filter(x -> x.findElement(footerGroupTitle).getText().equals(title)).findFirst().get();
		return getStringsFromElements(footer, footerGroupLinks);
	}
	
	public WebElement getFooterTitleOptionElement(String title, String option) {
		WebElement footer = findElements(footerGroupContainer).stream().filter(x -> x.findElement(footerGroupTitle).getText().equals(title)).findFirst().get();
		return getElementWithText(footer.findElements(footerGroupLinks), option);
	}
	
	public void clickTopNavBtn(String buttonName) {
		WebElement navElement = getElementWithText(findElements(topNavBtn), buttonName);
		click(navElement);
	}
	
	public void clickTopNavMobileBtn() {
		click(topMobileNavBtn);
	}
	
	public void clickMainNavBtn(String buttonName) {
		WebElement navElement = getElementWithText(findElements(mainNavBtn), buttonName);
		click(navElement);
		waitForElementPresence(mainNavCloseIcon);
	}
	
	public void clickMainNavContentBtn(String buttonName) {
		String beforeRedirect = getCurrentPageURL();
		waitUntilElementContainsText(findElements(mainNavContentOptions), buttonName);
		WebElement navElement = getElementWithText(findElements(mainNavContentOptions), buttonName);
		click(navElement);
		waitUntilConditionSatisfied(x -> {
			return !getCurrentPageURL().equals(beforeRedirect);
		});
	}
	
	public void clickMainNavSocialIconBtn(String socialSite) {
		WebElement element = getElementWithAttribute(findElements(mainNavSocialIcon), "data-icon", socialSite);
		click(element);
	}
	
	public void clickMainNavCloseLocator() {
		click(mainNavCloseIcon);
	}
	
	public void clickFooterOption(String title, String option) {
		WebElement optionElement = getFooterTitleOptionElement(title, option);
		click(optionElement);
	}
	
	public boolean isTopMobileNavElementDisplayed() {
		return isElementDisplayed(findElement(topMobileNavBtn));
	}
}
