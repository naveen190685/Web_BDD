package com.web.ui.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.paulhammant.ngwebdriver.ByAngular;
import com.web.ui.core.CommonHelper;

import io.appium.java_client.MobileElement;

public class RealtorSearchPage {
	CommonHelper helper;

	public RealtorSearchPage(CommonHelper helper) {
		this.helper = helper;
		PageFactory.initElements(helper.getDriver(), this);
	}

	By byCompanyNametxt = ByAngular.model("name");
	By byEmployeestxt = ByAngular.model("employees");
	By byHeadOfficetxt = ByAngular.model("headoffice");
	By bySubmitBtn = By.xpath("//input[@type = 'submit']");

	public void addCompanyName(String cName) {
		helper.type(byCompanyNametxt, cName);
		;
	}

	public void addEmployees(String cEmployees) {
		helper.type(byEmployeestxt, cEmployees);
	}

	public void addHeadoffice(String cHeadoffice) {
		helper.type(byHeadOfficetxt, cHeadoffice);
	}

	public void clickSumitBtn() {
		helper.click(bySubmitBtn);
		helper.getAngularDriver().waitForAngularRequestsToFinish();
	}

	public boolean checkNewDataAdded(String company, String employees, String headoffice) {
		boolean flag = false;
		List<WebElement> rows = helper.findElements(ByAngular.exactRepeater("company in companies"));
		for (WebElement row : rows) {
			if (row.findElement(By.className("ng-binding")).getText().toLowerCase().contains(company.toLowerCase())) {
				List<WebElement> dataTds = row.findElements(By.className("ng-binding"));
				if (dataTds.get(1).getText().toLowerCase().contains(employees.toLowerCase())) {
					if (dataTds.get(2).getText().toLowerCase().contains(headoffice.toLowerCase()))
						flag = true;

				}
			}
		}
		return flag;
	}

	public void search(String string) throws InterruptedException {
//		try {
		helper.getLocalDriver().findElement(
				By.xpath("//android.widget.TextView"
						+ "[@resource-id='com.move.realtor:id/on_boarding_location']")
				).click();
		Thread.sleep(4000);
		helper.getLocalDriver().findElement(
				By.xpath("//android.widget.TextView"
						+ "[@resource-id='com.move.realtor:id/search_edit_text']")
				).sendKeys(string);
		Thread.sleep(4000);
		helper.getLocalDriver().findElement(
				By.xpath("//android.widget.TextView"
						+ "[@name = 'Denver, CO']")).click();
		Thread.sleep(4000);
		helper.getLocalDriver().findElement(
				By.xpath("//android.widget.TextView"
						+ "[@resource-id= = 'com.move.realtor:id/view_list_label']")).click();
		Thread.sleep(4000);
		
//		}catch(Exception e) {
//			System.err.println("Error");
//		}
		 
		
	}

}
