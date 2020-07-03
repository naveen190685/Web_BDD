package com.web.ui.stepdefinition.common;

import org.openqa.selenium.WebDriverException;

import com.web.ui.core.CommonHelper;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {
	public CommonHelper helper;

	@Before
	public void setup(Scenario scr) throws Exception {
		CommonHelper.setScnr(scr);
		CommonHelper.setIsUItc(false);
		System.out.println("HOOKS BEFORE EXECUTED");
		System.out.println("Current Scenario: " + scr.getName());
	}

	@After
	public void attachScreensotOnFailure(Scenario scenario) {
		if (CommonHelper.getIsUItc()) {
			if (scenario.isFailed()) {
				try {
					scenario.embed(CommonHelper.getScreenshotInBytes(), "image/png");
				} catch (WebDriverException somePlatformsDontSupportScreenshots) {
					System.err.println(somePlatformsDontSupportScreenshots.getMessage());
				}
			}
			CommonHelper.cleanDriverSession();
			CommonHelper.setLocalDriver(null);
		}
	}
}