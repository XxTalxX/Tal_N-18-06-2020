package Automation;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class HeroloAutomationPage extends PageObject{

	public HeroloAutomationPage(WebDriver selectedWebDriver) {
		super(selectedWebDriver);
		// TODO Auto-generated constructor stub
	}

	public static String errorMessage;

	@FindBy(id="footer")
	private WebElement footer;

	@FindBy(xpath="//input[@name='name']")
	private WebElement nameInputBox;

	@FindBy(xpath="//input[@name='email']")
	private WebElement emailInputBox;

	@FindBy(xpath="//input[@name='phone']")
	private WebElement phoneInputBox;

	@FindBy(className="fYJjBk")
	private List<WebElement> inputError;

	@FindBy(css="#footer button")
	private WebElement footerSendButton;

	@FindBy(xpath="//*[@id='gatsby-focus-wrapper']/div/a[2]")
	private WebElement whatsAppButton;

	@FindBy(className="iCxNhL")
	private WebElement frontMenuSlider;

	@FindBy(className="gTBgIC")
	private WebElement cardsContainer;

	@FindBy(className="idRhPL")
	private WebElement jobsSlickSlider;

	@FindBy(className="bSKFKn")
	private WebElement customerSlickSlider;

	@FindBy(className="slick-prev")
	private WebElement jobsSlickPrevArrow;

	@FindBy(className="slick-next")
	private WebElement jobsSlickNextArrow;

	@FindBy(className="slick-dots")
	private List<WebElement> slickDots;

	@FindBy(className="TaXsW")
	private WebElement jobsSection;

	@FindBy(className="eDDkCa")
	private WebElement customersSection;
	
	@FindBy(className="jPKFmq")
	private WebElement socialMediaBar;
	
	@FindBy(className="jCiLrI")
	private WebElement linkedIn;
	
	@FindBy(className="iZfnyd")
	private WebElement whatsApp;
	
	@FindBy(className="gdrWsw")
	private WebElement faceBook;
	
	@FindBy(className="gAmXai")
	private WebElement herolo;
	
	@FindBy(className="fIqtKc")
	private WebElement backToTopButton;

	private List<WebElement> jobSlickDotsList = slickDots.get(0).findElements(By.tagName("li"));

	JavascriptExecutor javascriptExecutor = (JavascriptExecutor)selectedWebDriver;

	private boolean checkCurrentJobSlide(int slideIndex){

		int calculatedSlideIndex = ( slideIndex % jobSlickDotsList.size() + jobSlickDotsList.size() ) % jobSlickDotsList.size();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-index=" + "'" + String.valueOf(calculatedSlideIndex) + "'" + "]")));
		if(!this.jobsSlickSlider.findElement		
				(By.xpath("//div[@data-index=" + "'" + String.valueOf(calculatedSlideIndex) + "'" + "]")).getAttribute("class").contains("slick-current")) 
		{	
			errorMessage = "should show slide number: " + calculatedSlideIndex;
			return false;
		}
		if(!this.jobSlickDotsList.get(calculatedSlideIndex).getAttribute("class").contains("active")) {

			errorMessage = "should show slide dot number: " + calculatedSlideIndex;
			return false;
		}
		return true;
	}

	public boolean moveJobsSlides(EArrowDirection direction, int numberOfClicks) {
		wait.until(ExpectedConditions.visibilityOf(this.jobsSlickSlider));
		javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", this.jobsSlickSlider);
		WebElement selectedArrow;
		int loopIndex = 0;
		int sign = 1;
		if(direction == EArrowDirection.Next) {
			selectedArrow = this.jobsSlickNextArrow;
		}else { 
			selectedArrow = this.jobsSlickPrevArrow;
			sign = -1;
		}
		for (loopIndex = 0; loopIndex <= numberOfClicks; loopIndex++) {
			wait.until(ExpectedConditions.elementToBeClickable(selectedArrow));
			wait.until(ExpectedConditions.visibilityOfAllElements(this.jobSlickDotsList));
			org.junit.Assert.assertTrue(errorMessage,checkCurrentJobSlide(loopIndex*sign));
			wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(this.jobsSlickSlider.findElement(By.className("slick-track")), "style", "transition")));
			selectedArrow.click();
		}
		return true;
	}

	public boolean checkCustomerSlides(int numberOfChanges) {
		wait.until(ExpectedConditions.visibilityOf(this.customerSlickSlider));
		javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", this.customerSlickSlider);
		int transitionsRecorded = 0;
		for (int i = 0; i < numberOfChanges; i++) {
			wait.until(ExpectedConditions.attributeContains(this.customerSlickSlider.findElement(By.className("slick-track")), "style", "transition"));
			wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(this.customerSlickSlider.findElement(By.className("slick-track")), "style", "transition")));
			transitionsRecorded = transitionsRecorded + 1;
		}
		errorMessage = "tranisition number inaccurate, should be: " + numberOfChanges + " but was: " + transitionsRecorded;
		org.junit.Assert.assertTrue(errorMessage, transitionsRecorded == numberOfChanges);
		return true;
	}

	public boolean checkSlidingAnimation (ESlideAnimation section) {
		WebElement choosenSection;
		if(section == ESlideAnimation.Jobs) {
			choosenSection = this.jobsSection;;
			wait.until(ExpectedConditions.visibilityOf(choosenSection));
			javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", choosenSection);
		}
		else { 
			choosenSection = this.customersSection;
			wait.until(ExpectedConditions.visibilityOf(choosenSection));
			javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", choosenSection);
		}
		errorMessage = "Animation did not displayed correctly";
		org.junit.Assert.assertTrue(errorMessage, choosenSection.findElement(By.className("aos-init")).getAttribute("class").contains("aos-animate"));
		return true;

	}

	public boolean inputContactDetail(EChoosenInput choosenInput, String detail) {
		wait.until(ExpectedConditions.visibilityOf(footer));
		switch(choosenInput) {
		case Name:
			nameInputBox.click();
			nameInputBox.sendKeys(detail);
			errorMessage = "invalid name input";
			org.junit.Assert.assertFalse(errorMessage,inputError.size() > 0);
			break;
		case Email:
			wait.until(ExpectedConditions.visibilityOf(footer));
			emailInputBox.click();
			emailInputBox.sendKeys(detail);
			errorMessage = "invalid email input";
			org.junit.Assert.assertFalse(errorMessage,inputError.size() > 0);
			break;	
		case Phone:
			wait.until(ExpectedConditions.visibilityOf(footer));
			phoneInputBox.click();
			phoneInputBox.sendKeys(detail);
			errorMessage = "invalid phone input";
			org.junit.Assert.assertFalse(errorMessage,inputError.size() > 0);
			break;
		default:
			return false;
		}
		return true;
	}

	boolean clickSendInFooter() {
		wait.until(ExpectedConditions.visibilityOf(footer));
		footerSendButton.click();
		errorMessage = "did not send, inputs invalid";
		org.junit.Assert.assertFalse(errorMessage,inputError.size() > 0);
		return true;
	}
	

	boolean socialMediaContact(ESocialMedia socialMedia) {
		wait.until(ExpectedConditions.visibilityOf(this.socialMediaBar));
		javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", this.socialMediaBar);
		String url;
		errorMessage = "wrong url presented";
		List<WebElement> soicalMediaElements = this.socialMediaBar.findElements(By.tagName("a"));
		for (ESocialMedia sm : ESocialMedia.values()) { 
			if(sm.toString().equals(socialMedia.toString())) {
				for (WebElement webElement : soicalMediaElements) {
					if(webElement.getAttribute("href").contains(socialMedia.toString().toLowerCase() + ".")) {
						webElement.click();
						wait.until(ExpectedConditions.numberOfWindowsToBe(2));
						url = selectedWebDriver.getCurrentUrl();
						org.junit.Assert.assertTrue(errorMessage, url.toLowerCase().contains(socialMedia.toString().toLowerCase()));
						return true;
					}
				} 
			} 
		} 
		
		return false;
	}
	

	
	boolean clickBackToTopButton()  {
		javascriptExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		wait.until(ExpectedConditions.visibilityOf(this.backToTopButton));
		errorMessage = "backToTop button not working";
		Long bottomValue = (Long) javascriptExecutor.executeScript("return window.pageYOffset;");
		this.backToTopButton.click();
		Long topValue = (Long) javascriptExecutor.executeScript("return window.pageYOffset;");
		org.junit.Assert.assertTrue(errorMessage, bottomValue > topValue);
		return true;
		
	}
	
	
	


	enum EArrowDirection {Previous,Next};
	enum ESlideAnimation {Jobs,Customers};
	enum EChoosenInput   {Name,Phone,Email};
	enum ESocialMedia {LinkedIn,FaceBook,WhatsApp,Herolo};
}

