package com.web.ui.stepdefinition;

import com.web.ui.page.AngularSamplePage;
import com.web.ui.page.RealtorSearchPage;

import cucumber.api.java.en.Given;

public class RealtorSearchPage_StepDef {
	
	RealtorSearchPage page;	
	String company, employees, headoffice;
	
	public RealtorSearchPage_StepDef(RealtorSearchPage page) {
		this.page = page;
	}
	
	@Given("^Search for \"([^\"]*)\" properties$")
	public void search_for_properties(String arg1) throws Throwable {
	    page.search("Denver");
	}
	
}