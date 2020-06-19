package Automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObject {

	protected WebDriver selectedWebDriver;
	protected static WebDriverWait wait; 
	
	public PageObject(WebDriver selectedWebDriver)
	{
		this.selectedWebDriver = selectedWebDriver;
		wait = new WebDriverWait(selectedWebDriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated((By.id("logom"))));
		PageFactory.initElements(selectedWebDriver, this);
	}
	
}
