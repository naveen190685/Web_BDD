//package Templates;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.TimeoutException;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//import com.rogers.ui.core.CommonHelper;
//
////Replace TemplatePage with your page class name
//public class TemplatePage{
//	CommonHelper helper;
//	
//	//Replace TemplatePage with your page class name
//	public TemplatePage(CommonHelper helper) {
//		this.helper = helper;
//		PageFactory.initElements(helper.getDriver(), this);
//	}
//	
//	/* LOCATOR LEVEL: 1
//	 * This section contain locators for UI element readily available when the page loaded
//	 * Element having id or name. 
//	 * Just keep the WebElement's object name as the id or name of the target UI element
//	 */
//	WebElement signInLink; //This will assume your UI element has an id or name = "signIn"
//	
//	/* LOCATOR LEVEL: 2
//	 * This section will contain locators that are not possibly located by id or name
//	 * But these are element are readily available when the page is loaded
//	 */
//	 @FindBy(xpath = "//myXpath") //xpath is replacable with your choice of locator
//	 WebElement myElemen;
//	 
//	 /* LOCATOR LEVEL: 3
//	  * This section contain By locators for elements that pop up on 
//	  * some event like clicking some link or button 
//	  */	
//	By userName = By.id("username");
//	By password = By.id("password");
//	By SignInBtn = By.xpath("//button[contains(text(), 'Sign in')]");
//	By signInFrame = By.xpath("//div[@id = 'signin-interceptor-modal']//iframe");
//	By errorMsg = By.xpath("//small[@class = 'validation-error']");
//	By recoveryErrors = By.xpath("//div[@class = 'recovery-errors']/span");
//	
//	//Below section will contain action you can perform on page
//	//You can perform direct action with LEVEL 1 locators
//	//USE the helper object to perform any action with the LEVEL 3 locators
//	//For the 
//	
//	public void signIn(String user, String pass) throws Exception {
//		signInLink.click();									//LEVEL 1 & 2 locator action example
//		helper.switchToFrameUsingLocator(signInFrame);		//LEVEL 3 locator actions by using helper object
//		helper.type(userName, user);
//		helper.type(password, pass);
//		helper.click(SignInBtn);  
//	}
//}
