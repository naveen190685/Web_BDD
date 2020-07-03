//package Templates;
//
//import com.rogers.ui.page.TemplatePage;
//import cucumber.api.java.en.Given;
//import cucumber.api.java.en.Then;
//import cucumber.api.java.en.When;
//import junit.framework.Assert;
//
//public class TemplatePage_StepDef {
//	TemplatePage page;
//
//	public TemplatePage_StepDef(TemplatePage page) {
//		this.page = page;
//	}
//
//	@Given("^user is on SignIn page$")
//	public void user_is_on_SignIn_page() throws Throwable {
//		// Write code here that turns the phrase above into concrete actions
//		System.out.println("Executing BACKGROUND");page.openSigninModule();
//		page.openSigninModule();
//	}
//
//	@When("^the customer wants to login$")
//	public void customer_wants_to_login() throws Exception {
//		page.openSigninModule();
//	}
//
//	@When("^user login with credentails as \"([^\"]*)\" and \"([^\"]*)\"$")
//	public void user_login_with_credentails_as_and(String arg1, String arg2) throws Throwable {
//		// Write code here that turns the phrase above into concrete actions
//
//		page.submitCredentials(arg1, arg2);
//	}
//
//	@Then("^error message should be shown with text \"([^\"]*)\"$")
//	public void error_message_should_be_shown_with_text(String arg1) throws Throwable {
//		// Write code here that turns the phrase above into concrete actions
//		Assert.assertTrue("Actual error: " + page.error(), page.error().equals(arg1));
//	}
//
//	@Given("^customer is on rogers\\.com/fido\\.ca$")
//	public void customer_is_on_rogers_com_fido_ca() {
//		// Write code here that turns the phrase above into concrete actions
//		page.getToCustomerHome();
//	}
//
//	@Then("^they must provide a username/email and password to login$")
//	public void they_must_provide_a_username_email_and_password_to_login() throws Throwable {
//		// Write code here that turns the phrase above into concrete actions
//		page.submitSignIn();
//		Assert.assertTrue(!page.error().trim().isEmpty());
//	}
//	
//	@Then("^they should be provided with a pop-up modal that allows them to sign in or close$")
//	public void they_should_be_provided_with_a_pop_up_modal_that_allows_them_to_sign_in_or_close() throws Throwable {
//		// Write code here that turns the phrase above into concrete actions
//		Assert.assertTrue("SignIn model not displayed", page.isSignModelDisplayedWithLoginProvision());
//	}
//
//
//
//}
