package com.web.ui;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;

public class generateReport {

	public static void main(String[] args) throws IOException {

		String PATH_TO_SAVE = "reports/report_"
				+ (new Date(System.currentTimeMillis())).toString().replaceAll("[: ]", "_");
		File reportDir = new File(PATH_TO_SAVE);
		if (!reportDir.exists())
			FileUtils.forceMkdir(reportDir);

		System.out.println("Generating local HTML reports for Cucumber");
		File reportOutputDirectory;
		if (new File("target/cucumber-parallel").exists())
			reportOutputDirectory = new File("target/cucumber-parallel");
		else
			reportOutputDirectory = new File("target");

		FileFilter fileFilter = new WildcardFileFilter("*.json");
		File[] files = reportOutputDirectory.listFiles(fileFilter);
		List<String> jsonFiles = new ArrayList<String>();
		for (File file : files) {
			jsonFiles.add(file.getAbsolutePath());
		}

		String buildNumber = System.getenv("BUILD_NUMBER") == null ? "1" : System.getenv("BUILD_NUMBER");
		String projectName = "OnelinkBDD";
		boolean runWithJenkins = false;
		boolean parallelTesting = false;

		// Modified below so the reports can be generated here
		reportOutputDirectory = new File(PATH_TO_SAVE);

		Configuration configuration = new Configuration(reportOutputDirectory, projectName);
		// optional configuration
		configuration.setParallelTesting(parallelTesting);
		configuration.setRunWithJenkins(runWithJenkins);
		configuration.setBuildNumber(buildNumber);

		ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
		Reportable result = reportBuilder.generateReports();

	}

}
