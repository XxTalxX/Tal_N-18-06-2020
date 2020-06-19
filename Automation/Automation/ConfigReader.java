package Automation;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class ConfigReader {
	
	private static ConfigReader _configReader;
	protected static URL url;
	static String user_dir = System.getProperty("user.dir");
	
	private ConfigReader() throws MalformedURLException
	{
		url = new URL("https://automation.herolo.co.il/");
	}
	
	public static ConfigReader getInstance() throws MalformedURLException
	{
		if(_configReader == null)
		{
			_configReader = new ConfigReader();
		}
		return _configReader;
	}
	
	public static WebDriver setBrowserType(EpropertyType browserType){
		WebDriver selectedWebDriver = null;
		if(browserType.equals(EpropertyType.Chrome))
		{
			System.setProperty("webdriver.chrome.driver", user_dir + "/chromedriver.exe");
			selectedWebDriver = new ChromeDriver();
		}
		else if(browserType.equals(EpropertyType.Edge))
		{
		System.setProperty("webdriver.edge.driver", user_dir + "/msedgedriver.exe");
		selectedWebDriver = new EdgeDriver();
		}
		//.... continue for other webdrivers...
		return selectedWebDriver;
		
	}
	
	public static int checkStatus() throws IOException
	{ 
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      conn.connect();
	      return conn.getResponseCode();
	}
	
	enum EpropertyType{Chrome,Edge};
	
}
