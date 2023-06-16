package com.qustodio.qustodioapp.pageObjects.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.qustodio.qustodioapp.utils.AndroidActions;

import java.util.List;

public class ProtectDevicePage extends AndroidActions{

	AndroidDriver driver;

	public ProtectDevicePage(AndroidDriver driver) {
		super(driver);
		this.driver =driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id="com.qustodio.qustodioapp:id/kidsButton")
	private WebElement protectThisDeviceButton;

	@AndroidFindBy(id="com.qustodio.qustodioapp:id/idDeviceNameInputEditText")
	private WebElement deviceNameField;

	@AndroidFindBy(id="com.qustodio.qustodioapp:id/button")
	private WebElement continueButton;

	@AndroidFindBy(xpath = "//android.widget.TextView")
	private List<WebElement> whoUsesThisDeviceElements;

	@AndroidFindBy(id = "android:id/button1")
	private WebElement replaceExistingDevice;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Who uses this device?']")
	private WebElement deviceNameLabel;

	@AndroidFindBy(id = "com.qustodio.qustodioapp:id/closeButton")
	private WebElement enableQustodioButton;

	public void protectDevice(String deviceName, String whoUsesThisDevice) {
		click(protectThisDeviceButton);
		sendKeys(deviceNameField, deviceName);
		click(continueButton);
		click(replaceExistingDevice);
		waitFor(ExpectedConditions.visibilityOf(deviceNameLabel));
		WebElement whoUsesThisDeviceElement = returnElementByText(whoUsesThisDeviceElements, whoUsesThisDevice);
		click(whoUsesThisDeviceElement);
		click(enableQustodioButton);
	}

}
