package com.web.ui;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(

		plugin = { "pretty", "html:target/cucumber", "json:target/cucumber.json" },
		/***** For Sample Genereated Run ***********/
		features = { "src/test/resources/features" }, glue = { "com.onelink.ui.stepdefinition",
				"com.onelink.api.stepdefinition" },
		/***** End of Sample Generated Run ********/
		tags = { "@Realtop_App" })

public class TestRunner {

	@BeforeClass
	public static void getSetGo() {

	}
}