package com.web.ui.stepdefinition.common;

import org.junit.Assert;
import org.openqa.selenium.WebElement;

import com.web.ui.core.LoadDomainConfig;
import com.web.ui.page.common.Common;

import cucumber.api.java.en.*;

public class Common_StepDef {
	
	Common cpage;
	WebElement Security;
	WebElement SecurityLockImage;

	public Common_StepDef(Common cpage) {
		this.cpage = cpage;
	}	

	
	@Given("^user is on hello-angularjs\\.appspot\\.com/addtablerow$")
	public void user_is_on_hello_angularjs_appspot_com_addtablerow() throws Throwable {		
		LoadDomainConfig.loadDomainConfig("AngularSample", "QA");
		cpage.getToCustomerHome();
	}
	
	@Then("^the user should see Security Tab with lock image$")
	public void the_user_should_see_Security_Tab_with_lock_image() throws Throwable {
	    Assert.assertTrue("Security Tab missing", Security.isDisplayed());
	    Assert.assertTrue("Security Image missing", 
		    	SecurityLockImage.isDisplayed());
	}
	
	@When("^the user clicks on Security tab$")
	public void the_user_clicks_on_Security_tab() throws Throwable {
	   Security.click();
	}
	
	@Given("^user is logged into One Link application$")
	public void user_is_logged_into_One_Link_application() throws Throwable {
		LoadDomainConfig.loadDomainConfig("Onelink", "QA");
	}
	
	@Given("^user launched realtor on android$")
	public void user_launched_realtor_on_android() throws Throwable {
		LoadDomainConfig.loadDomainConfig("AngularSample", "QA");
	}
}
