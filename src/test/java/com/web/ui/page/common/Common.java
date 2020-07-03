package com.web.ui.page.common;

import com.web.ui.core.CommonHelper;

public class Common {
	
	CommonHelper helper;
	public Common(CommonHelper helper) {
        this.helper = helper;
    }
	
	public void getToCustomerHome(){
		try {
			helper.launchBrowserWithUrl(CommonHelper.getUrl());
			helper.getAngularDriver().waitForAngularRequestsToFinish();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
