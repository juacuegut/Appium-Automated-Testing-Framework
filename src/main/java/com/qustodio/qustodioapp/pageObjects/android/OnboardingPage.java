package com.qustodio.qustodioapp.pageObjects.android;

import com.qustodio.qustodioapp.utils.AndroidActions;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class OnboardingPage extends AndroidActions {

	AndroidDriver driver;

	public OnboardingPage(AndroidDriver driver)
	{
		super(driver);
		this.driver =driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(id="com.qustodio.qustodioapp:id/loginText")
	private WebElement logInLinkButton;

	@AndroidFindBy(id="com.qustodio.qustodioapp:id/idUserEmailInputEditText")
	private WebElement emailField;

	@AndroidFindBy(id="com.qustodio.qustodioapp:id/idUserPasswordInputEditText")
	private WebElement passwordField;

	@AndroidFindBy(id="com.qustodio.qustodioapp:id/button")
	private WebElement logInButton;

	public void performLogIn(String email, String password)
	{
		click(logInLinkButton);
		sendKeys(emailField,email);
		sendKeys(passwordField,password);
		click(logInButton);
	}

}
