package com.web.ui.core;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.xerces.util.URI;
import org.apache.xerces.util.URI.MalformedURIException;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import net.sourceforge.htmlunit.corejs.javascript.ast.SwitchCase;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class CommonHelper extends Core {
	public CommonHelper() throws Exception {
		// super();
		// TODO Auto-generated constructor stub
	}

	public static byte[] getScreenshotInBytes() {
		return ((TakesScreenshot) getLocalDriver()).getScreenshotAs(OutputType.BYTES);
	}

	public void takeScreenshot() {
		screenShoot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
				.takeScreenshot(getLocalDriver());
		try {
			ImageIO.write(screenShoot.getImage(), "PNG", new File("target/" + CommonHelper.getScnr().getName()
					+ (new Date(System.currentTimeMillis())).toString().replaceAll("[: ]", "_") + ".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ExpectedCondition ElementPresent(By by) {
		return ExpectedConditions.presenceOfElementLocated(by);
	}

	public ExpectedCondition ElementPresent(WebElement ele) {
		return ExpectedConditions.visibilityOf(ele);
	}

	public ExpectedCondition ElementClickable(By by) {
		return ExpectedConditions.elementToBeClickable(by);
	}

	public ExpectedCondition AlertPresent() {
		return ExpectedConditions.alertIsPresent();
	}

	public ExpectedCondition NumberOfFrames(int i) {
		return ExpectedConditions.numberOfwindowsToBe(i);
	}

	public String getCurrentUrl() {
		return getLocalDriver().getCurrentUrl();
	}

	public WebElement findElement(By by) {
		getWait().until(ElementPresent(by));
		return getLocalDriver().findElement(by);
	}

	public void closeDriver() {
		getLocalDriver().close();
	}

	public void quitDriver() {
		getLocalDriver().quit();
	}

	public String getText(By by) {
		return findElement(by).getText();
	}

	public void click(By by) {
		findElement(by).click();
	}

	public void clickLink(String linktext) {
		findElement(By.linkText(linktext)).click();
	}

	public void type(By by, String str) {
		findElement(by).sendKeys(str);
	}

	/******************* NAVIGATION FUNCTIONS *********************/
	/**
	 * @param url <b>Description</b> Launches the browser and direct to the given
	 *            url
	 * @throws Exception
	 */
	public void launchBrowserWithUrl(String url) throws Exception {
		try {
			URI pageurl = new URI(url);
			getLocalDriver().get(url);
			getLocalDriver().manage().deleteAllCookies();
			getLocalDriver().manage().window().maximize();
		} catch (MalformedURIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Invalid url format");
		}
	}

	public void navigateToUrl(String url) {
		if (!getCurrentUrl().equals(url)) {
			getLocalDriver().navigate().to(url);
		}
	}

	public Boolean isDisplayed(By by) {
		return findElement(by).isDisplayed();
	}

	public void switchToLatestWindow() {
		List handlers = (List) getLocalDriver().getWindowHandles();
		getLocalDriver().switchTo().window(handlers.get(handlers.size()).toString());
	}

	public List<WebElement> findElements(By by) {
		return getLocalDriver().findElements(by);
	}

	/**
	 * @param by
	 * @param indexNumber <b>Description</b> Gets an element at indexNumber for
	 *                    elements located by "by"
	 * @return WebElement
	 */
	public WebElement getElementAtIndex(By by, int indexNumber) {
		return findElements(by).get(indexNumber);
	}

	/**
	 * @param by
	 * @param selection <b>Description</b> Selects selection from a dropdown
	 */
	public void selectFromDropdown(By by, String selection) {
		new Select(findElement(by)).selectByVisibleText(selection);
	}

	/**
	 * @param by <b>Description</b> Checks the checkbox
	 */
	public void checkCheckbox(By by) {
		if (findElement(by).isSelected()) {
		} else
			findElement(by).click();
	}

	/**
	 * @param by <b>Description</b> Checks the checkbox
	 */
	public void uncheckCheckbox(By by) {
		System.out.println("*******************" + findElement(by).isSelected());
		if (findElement(by).isSelected())
			findElement(by).click();
	}

	/**
	 * @param frameId <b>Description</b> Switch to the frame with provided Id
	 */
	public void switchToFrameUsingId(String frameId) {
		getLocalDriver().switchTo().frame(frameId);
	}

	/**
	 * @param frameId <b>Description</b> Switch to the frame with provided index
	 */
	public void switchToFrameUsingIndex(int index) {
		getLocalDriver().switchTo().frame(index);
	}

	/**
	 * @param frameId <b>Description</b> Switch to the frame with provided locator
	 */
	public void switchToFrameUsingLocator(By by) {
		getLocalDriver().switchTo().frame(findElement(by));
	}

	public void switchToFrameUsingLocator(WebElement frameElement) {
		getLocalDriver().switchTo().frame(frameElement);
	}

	/**
	 * <b>Description</b> Switch to default content of page i.e. out of all frames
	 */
	public void switchToDefaultContent() {
		getLocalDriver().switchTo().defaultContent();
	}

	public WebDriver getDriver() {
		// TODO Auto-generated method stub
		return getLocalDriver();
	}

	// SOME SPECIAL LOCATORS//

	private By getMyLocator(String locator) throws Exception {
		By by = null;
		String[] key = locator.split(":");
		if (key.length ==2) {
			key[0] = key[0].toLowerCase();
			key[1] = key[1].trim();
			switch (key[0]) {
			case "id":
				by = By.id(key[1]);
				break;
			case "name":
				by = By.name(key[1]);
				break;
			case "xpath":
				by = By.xpath(key[1]);
				break;
			case "linktext":
				by = By.linkText(key[1]);
				break;
			case "particallinktext":
				by = By.partialLinkText(key[1]);
				break;

			default:
				break;
			}
		} else if (key.length ==1) {
			by = By.id(locator);
		} else throw new Exception(
				"Please provide locator in format--> locatorType: locator");
		return by;
	}
	
	/**
	 * @param locator is considered id by default
	 */
	public void click(String id) {
		click(By.id(id));
	}
	
	public void click() {
		click(By.tagName("button"));
	}

	public void clickButton(String id) {
		click(By.xpath("//input[@id = '" + id + "']"));
	}
	
	public void write(String content) {
		type(By.tagName("input"), content);
	}
	
	public void goTo(String url) throws MalformedURLException {
		URL ur = new URL(url);
		navigateToUrl(url);
	}

}