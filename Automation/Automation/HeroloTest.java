package Automation;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import Automation.ConfigReader;
import Automation.ConfigReader.EpropertyType;
import Automation.HeroloAutomationPage.EArrowDirection;
import Automation.HeroloAutomationPage.EChoosenInput;
import Automation.HeroloAutomationPage.ESlideAnimation;
import Automation.HeroloAutomationPage.ESocialMedia;

public class HeroloTest {
	
	public static WebDriver selectedWebDriver;
	public static int responseCode;
	public static boolean expectedStatus;
	public static HeroloAutomationPage heroloAutomationPage;

	
	@Before
	public void setup() throws IOException{
	ConfigReader.getInstance();
	responseCode = ConfigReader.checkStatus();
	Assert.assertFalse("statuse response is 400", responseCode == 400);
	selectedWebDriver = ConfigReader.setBrowserType(EpropertyType.Chrome);
//	heroloAutomationPage = new HeroloAutomationPage(selectedWebDriver);
	selectedWebDriver.get(ConfigReader.url.toString());
	selectedWebDriver.manage().window().maximize();
	heroloAutomationPage = new HeroloAutomationPage(selectedWebDriver);
	}
	
	
	@Test
	/*
	 * check that jobs section sliding animation works as intended upon scrolling, expect to succeed
	 */
	public void testJobsSlidingAnimation() {
		expectedStatus = heroloAutomationPage.checkSlidingAnimation(ESlideAnimation.Jobs);
		Assert.assertTrue(expectedStatus);
	}
	
	@Test
	/*
	 * check that customers section sliding animation works as intended upon scrolling, expect to succeed
	 */
	public void testCustomersSlidingAnimation() {
		expectedStatus = heroloAutomationPage.checkSlidingAnimation(ESlideAnimation.Customers);
		Assert.assertTrue(expectedStatus);
	}
	
	@Test
	/*
	 * move jobs section carousel forward, expect to succeed
	 */
	public void testJobsCarouselForward() {
	
	expectedStatus = heroloAutomationPage.moveJobsSlides(EArrowDirection.Next, 6);
	Assert.assertTrue(expectedStatus);

	}
	@Test
	/*
	 * move jobs section carousel backward, expect to succeed
	 */
	public void testJobsCarouselBackward() {
	
	expectedStatus = heroloAutomationPage.moveJobsSlides(EArrowDirection.Previous, 3);
	Assert.assertTrue(expectedStatus);

	}
	
	@Test
	/*
	 * monitor customer section automatic slide, expect to succeed
	 */
	public void testCustomerSlickSlider() {
		expectedStatus = heroloAutomationPage.checkCustomerSlides(4);
		Assert.assertTrue(expectedStatus);
	}
	
	@Test
	/*
	 * test back to top button, expect to succeed
	 */
	public void testBackToTop() {
		expectedStatus = heroloAutomationPage.clickBackToTopButton();
		Assert.assertTrue(expectedStatus);
	}
	
	@Test
	/*
	 * input name and email at footer contact section, expect to succeed
	 */
	public void inputNameAndEmailContactDetails() {
		expectedStatus = heroloAutomationPage.inputContactDetail(EChoosenInput.Name, "Tal");
		Assert.assertTrue(expectedStatus);
		expectedStatus = heroloAutomationPage.inputContactDetail(EChoosenInput.Email, "Tal@Email.com");
		Assert.assertTrue(expectedStatus);
	}
	
	@Test
	/*
	 * input invalid phone number, expect to fail
	 */
	public void inputPhoneContactDetails() {
		expectedStatus = heroloAutomationPage.inputContactDetail(EChoosenInput.Phone, "Tal");
		Assert.assertFalse(HeroloAutomationPage.errorMessage,expectedStatus);
	}
	
	@Test
	/*
	 * click send button with empty inputs, expect to fail
	 */
	public void clickSendButtonInFooter() {
		expectedStatus = heroloAutomationPage.clickSendInFooter();
		Assert.assertFalse(HeroloAutomationPage.errorMessage,expectedStatus);
	}
	@Test
	/*
	 * click on sociel media button choose herolo, expect to succeed
	 */
	public void openSocielMedia() {
		expectedStatus = heroloAutomationPage.socialMediaContact(ESocialMedia.Herolo);
		Assert.assertTrue(expectedStatus);
	}
	
	 @After
	    public void cleanUp(){
	        selectedWebDriver.manage().deleteAllCookies();
			selectedWebDriver.close();
			selectedWebDriver.quit();


	    }
	
}
