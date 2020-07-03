package com.web.ui.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.paulhammant.ngwebdriver.ByAngular;
import com.web.ui.core.CommonHelper;

public class AngularSamplePage {
	CommonHelper helper;

	public AngularSamplePage(CommonHelper helper) {
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

}
