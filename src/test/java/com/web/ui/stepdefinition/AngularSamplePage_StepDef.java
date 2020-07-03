package com.web.ui.stepdefinition;

import com.web.ui.page.AngularSamplePage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AngularSamplePage_StepDef {
	
	AngularSamplePage page;	
	String company, employees, headoffice;
	
	public AngularSamplePage_StepDef(AngularSamplePage page) {
		this.page = page;
	}
	
	@Given("^add company name as \"([^\"]*)\"$")
	public void add_company_name_as(String arg1) throws Throwable {
		company = arg1;
	    page.addCompanyName(arg1);
	}
	    
	@Given("^add employee names as \"([^\"]*)\"$")
	public void add_employee_names_as(String arg1) throws Throwable {
		employees = arg1;
	    page.addEmployees(arg1);
	}

	@Given("^add Headoffice name as \"([^\"]*)\"$")
	public void add_Headoffice_name_as(String arg1) throws Throwable {
		headoffice = arg1;
	    page.addHeadoffice(arg1);
	}

	@When("^submit button to add$")
	public void submit_button_to_add() throws Throwable {
	    page.clickSumitBtn();
	}

	@Then("^new row should be shown with details as \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
	public void new_row_should_be_shown_with_details_as_and(String arg1, String arg2, String arg3) throws Throwable {
	    org.junit.Assert.assertTrue("Data didn't get added", 
	    		page.checkNewDataAdded(company, employees, headoffice));
	}
}