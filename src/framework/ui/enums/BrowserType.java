package framework.ui.enums;

import java.util.Arrays;

public enum BrowserType {
	CHROME("chrome"), INTERNET_EXPLORER("ie"), FIREFOX("firefox"), SAFARI("safari"), EDGE("edge"), HEADLESS("headless");

	private String browser;

    BrowserType(String browser) {
        this.browser = browser;
    }

    public String getbrowser() {
        return this.browser;
    }

    public static BrowserType getBrowserEnum(String browser) {
        return Arrays.stream(values())
          .filter(bt -> bt.browser.equalsIgnoreCase(browser))
          .findFirst()
          .orElse(null);
    }
}
