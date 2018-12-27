package framework.ui.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import framework.ui.seleniumConfiguration.DriverFactory;

/**
 * Created by R.Kelly on 8/23/18.
 */

public class PageObject {
	private static WebDriverWait wait;
	protected static final int WAIT_SECONDS_SHORT = 10;
	protected static final int WAIT_FOR_SECONDS_MEDIUM = 30;
	protected static final int WAIT_FOR_SECONDS_LONG = 100;
	protected static final int POLLING_TIME_MILLIS = 100;

	public void click(WebElement element) {
		FluentWait<WebDriver> wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), WAIT_FOR_SECONDS_MEDIUM,
				POLLING_TIME_MILLIS).ignoring(WebDriverException.class);
		wait.until(x -> {
			element.click();
			return true;
		});
	}

	public void click(By elementLocator) {
		WebElement element = findElement(elementLocator);
		click(element);
	}

	public void sendKeys(WebElement element, String value) {
		element.sendKeys(value);
		waitForFieldToBePopulated(element, value);
	}

	public void sendKeys(By locator, String value) {
		sendKeys(findElement(locator), value);
	}

	public void sendKeysWithoutValidation(WebElement element, String value) {
		waitForElementPresence(element);
		element.sendKeys(value);
	}

	public void clearKeys(By element) {
		WebElement webElement = findElement(element);
		clearKeys(webElement);
	}

	public void clearKeys(WebElement element) {
		FluentWait<WebDriver> wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), WAIT_FOR_SECONDS_MEDIUM,
				POLLING_TIME_MILLIS).ignoring(StaleElementReferenceException.class)
						.ignoring(NoSuchElementException.class).ignoring(InvalidElementStateException.class);
		wait.until(webDriver -> {
			element.clear();
			return element.getAttribute("value").equals("");
		});
	}

	// Waits
	public void waitForElementPresence(By locator) {
		wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), WAIT_FOR_SECONDS_MEDIUM);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public void waitForElementPresence(WebElement element) {
		wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), WAIT_FOR_SECONDS_MEDIUM);
		wait.until(x -> {
			return element.isDisplayed();
		});
	}

	public void waitForElementPresenceShort(By locator) {
		wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), WAIT_SECONDS_SHORT);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public void waitForElementPresenceLong(By locator) {
		wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), WAIT_FOR_SECONDS_LONG);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public void waitForElementToBeClickable(By elementLocator) {
		wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), WAIT_FOR_SECONDS_MEDIUM);
		wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
	}

	public void waitForElementToBeClickable(WebElement element) {
		wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), WAIT_FOR_SECONDS_MEDIUM);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void waitForElementToContainTxt(WebElement element, String txt) {
		wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), WAIT_FOR_SECONDS_MEDIUM);
		wait.until(ExpectedConditions.textToBePresentInElement(element, txt));
	}

	private void waitForElementToDisappear(By elementLocator, long timeoutInSeconds) {
		FluentWait<WebDriver> wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), timeoutInSeconds,
				POLLING_TIME_MILLIS).ignoring(StaleElementReferenceException.class);
		wait.until(x -> {
			return DriverFactory.getInstance().getDriver().findElements(elementLocator).size() == 0;
		});
	}

	public void waitForElementDisappears(By by) {
		waitForElementToDisappear(by, WAIT_FOR_SECONDS_MEDIUM);
	}

	public void waitForElementDisappearsLong(By by) {
		waitForElementToDisappear(by, WAIT_FOR_SECONDS_LONG);
	}

	public void waitForFieldToBePopulated(WebElement field, String value) {
		wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), WAIT_FOR_SECONDS_MEDIUM);
		wait.until(ExpectedConditions.textToBePresentInElementValue(field, value));
	}

	public void waitUntilConditionSatisfied(Function<WebDriver, Boolean> booleanCondition) {
		wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), WAIT_FOR_SECONDS_MEDIUM);
		wait.until(booleanCondition);
	}

	public void waitForTextToDissapear(String txt) {
		By textElement = By.xpath("//*[text()='" + txt + "']");
		waitForElementToDisappear(textElement, WAIT_FOR_SECONDS_MEDIUM);
	}

	public void waitForTextToDissapear(String txt, int waitInSeconds) {
		By textElement = By.xpath("//*[text()='" + txt + "']");
		waitForElementToDisappear(textElement, waitInSeconds);
	}

	public void waitForTextToBeVisible(String txt) {
		By textElement = By.xpath("//*[text()='" + txt + "']");
		wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), WAIT_FOR_SECONDS_MEDIUM);
		wait.until(ExpectedConditions.visibilityOfElementLocated(textElement));
	}

	public void waitUntilElementContainsText(List<WebElement> listOfElements, String expectedText) {
		waitUntilConditionSatisfied(x -> {
			return listOfElements.stream().filter(y -> y.getText().equals(expectedText)).findAny().isPresent();
		});
	}
	
	// Getters
	public List<String> getStringsFromElements(By locator) {
		return findElements(locator).stream().map(x -> x.getText().trim()).collect(Collectors.toList());
	}

	public List<String> getStringsFromElements(WebElement element, By locator) {
		return element.findElements(locator).stream().map(x -> x.getText().trim()).collect(Collectors.toList());
	}

	public List<String> getAttributeFromElements(By locator, String attribute) {
		return findElements(locator).stream().map(x -> x.getAttribute(attribute).trim()).collect(Collectors.toList());
	}
	
	public String getPageTitle() {
		return DriverFactory.getInstance().getDriver().getTitle();
	}

	public String getElementTxt(By element) {
		waitForElementPresence(element);
		WebElement webElement = DriverFactory.getInstance().getDriver().findElement(element);
		return webElement.getText().trim();
	}

	public String getElementTxt(WebElement element) {
		waitForElementPresence(element);
		return element.getText().trim();
	}

	public String getCurrentPageURL() {
		return DriverFactory.getInstance().getDriver().getCurrentUrl();
	}
	
	public WebElement getElementWithText(List<WebElement> listOfElements, String expectedText) {
		try {
			Stream<WebElement> elementsWithText = listOfElements.stream().filter(x -> x.getText().equals(expectedText));
			return elementsWithText.findFirst().get();
		} catch(java.util.NoSuchElementException e) {
			throw new java.util.NoSuchElementException("Element with text " + expectedText + " not found");
		}
	}

	public WebElement getElementWithAttribute(List<WebElement> listOfElements, String attribute, String attributeValue) {
		try {
			Stream<WebElement> elementsWithAttribute = listOfElements.stream().filter(x -> x.getAttribute(attribute).equals(attributeValue));
			return elementsWithAttribute.findFirst().get();
		} catch(java.util.NoSuchElementException e) {
			throw new java.util.NoSuchElementException("Element with attribute value " + attributeValue + " not found");
		}
	}
	
	public WebElement findElement(By byLocator) {
		waitForElementPresence(byLocator);
		return DriverFactory.getInstance().getDriver().findElement(byLocator);
	}

	public List<WebElement> findElements(By byLocator) {
		waitForElementPresence(byLocator);
		return DriverFactory.getInstance().getDriver().findElements(byLocator);
	}

	public List<WebElement> getChildrenElements(WebElement parentElement, By childrenElementLocator) {
		return parentElement.findElements(childrenElementLocator);
	}

	// Booleans
	public boolean isElementDisplayed(WebElement element) {
		return element.isDisplayed();
	}

	public boolean isElementPresent(By elementLocator) {
		try {
			waitForElementPresenceShort(elementLocator);
			return true;
		} catch (TimeoutException t) {
			return false;
		}
	}

	public boolean isElementPresentMedium(By elementLocator) {
		try {
			waitForElementPresence(elementLocator);
			return true;
		} catch (TimeoutException t) {
			return false;
		}
	}

	public boolean isElementDisplayed(List<WebElement> elements) {
		return elements.size() != 0;
	}

	// Other
	public void scrollToElement(WebElement element) {
		((JavascriptExecutor) DriverFactory.getInstance().getDriver())
				.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public static void pause(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException i) {
			i.printStackTrace();
		}
	}
	
	public void switchTabs(int tabPosition) {
		try{
			WebDriver driver = DriverFactory.getInstance().getDriver();
		    ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		    driver.switchTo().window(tabs.get(tabPosition));
		} catch(IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException(" The driver tab does not exist");
		}
	}
}