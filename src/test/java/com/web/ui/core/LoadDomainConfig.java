package com.web.ui.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.sourceforge.htmlunit.corejs.javascript.tools.shell.Environment;

public class LoadDomainConfig {

	/**
	 * A flag to determine whether the configuration file has already been loaded
	 * into memory
	 */
	private boolean isConfigLoaded = false;

	/**
	 * The location and name of the configuration file containing the properties
	 */
	private static String EnvProperties = "src" + File.separator + "test" + File.separator + "resources"
			+ File.separator + "Setup";
	/**
	 * A properties object to hold the configuration settings
	 */
	private static Properties configProperties = null;

	// /*
	// * An object to hold the AppLib instance.
	// */
	// private static final class ConfigHolder { // NOSONAR
	//
	// /**
	// * A variable to hold the singleton instance of the AppLib class.
	// */
	// private static final InitializeConfig INSTANCE = new InitializeConfig();
	//
	// }

	/**
	 * Initializes the configuration file so that properties can be read from it.
	 * <b>Domain</b>: Fido/Rogers/API \n
	 * <b>Enviroment</b> to pass as mentioned in properties file e.g.QA or whatever you decide\n 
	 * Username and Password considered same for all env of a domain\n
	 * @throws IOException
	 * @throws Exception
	 */
	public final static void loadDomainConfig(String domain, String environment) throws Exception {
		File configFile;
		configFile = new File(EnvProperties + File.separator + domain + ".properties");
		configProperties = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(configFile);
			configProperties.load(input);
		} catch (FileNotFoundException e) {
			System.out.println("No file found: " + e);
		} catch (IOException e) {
			System.out.println("Error reading the file: " + e);
		} finally {
			if (input != null)
				input.close();
		}
		CommonHelper.setUrl(getProperty(environment));
		if (domain.equalsIgnoreCase("rogers") || domain.equalsIgnoreCase("fido")) {
			CommonHelper.setUsername(getProperty("USERNAME"));
			CommonHelper.setPassword(getProperty("PASSWORD"));
		}
	}
	
	public final static void setEnv() throws Exception {
		File configFile = new File(EnvProperties + File.separator + "ExecutionEnv.properties");
		configProperties = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(configFile);
			configProperties.load(input);
		} catch (FileNotFoundException e) {
			System.out.println("No file found: " + e);
		} catch (IOException e) {
			System.out.println("Error reading the file: " + e);
		} finally {
			if (input != null)
				input.close();
		}
		
			//If browser is send as jenkins parameter or env variables
			if (System.getenv("browser") != null)CommonHelper.setBROWSER(System.getenv("browser"));
			//Else if browser is set from mvn command
			else if (System.getProperty("browser") != null)CommonHelper.setBROWSER(System.getProperty("browser"));
			//Else if browser is set from ExecutionEnv.properties
			else if (!(getProperty("BROWSER").equals("")))CommonHelper.setBROWSER(getProperty("BROWSER"));
		
//		if(System.getenv("BUILD_ID")!=null) CommonHelper.setEXECUTION_MACHINE("GRID");
//		else 
			CommonHelper.setEXECUTION_MACHINE(getProperty("ENV"));
		CommonHelper.setGRID_LOCATION(getProperty("GRID_LOCATION"));
		CommonHelper.setBROWSER_VERSION(getProperty("BROWSER_VERSION"));
		CommonHelper.setOS(getProperty("OS"));
		CommonHelper.setOS(getProperty("SAUCE_USERNAME"));
		CommonHelper.setOS(getProperty("SAUCE_ACCESSKEY"));
	}

	/**
	 * Returns a property from the property file.
	 * 
	 * @param String
	 *            PARAM
	 * @return String value in lower case for the parameter passed.
	 * @throws Exception 
	 */
	private final static String getProperty(String PARAM) throws Exception {
		
		//If browser is send as jenkins parameter or env variables
		if (System.getenv(PARAM) != null)return System.getenv(PARAM);
		//Else if browser is set from mvn command
		else if (System.getProperty(PARAM) != null)return System.getProperty(PARAM);
		//Else if browser is set from ExecutionEnv.properties
		else if (!(getPropertyFromFile(PARAM).equals("")))return getPropertyFromFile(PARAM);
		else throw new Exception("Please add some parameter with name "+PARAM+" as env/mavenVariable/propertiesFile");
	}
	
	private final static String getPropertyFromFile(String PARAM) {
		try {
		return configProperties.getProperty(PARAM).toString().trim();
		} catch(NullPointerException e) {
			System.out.println("No param found with name "+PARAM);
			throw e;
		}
	}
}