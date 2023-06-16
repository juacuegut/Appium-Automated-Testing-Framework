package com.qustodio.qustodioapp.pageObjects.android;

import com.qustodio.qustodioapp.enums.Processes;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.qustodio.qustodioapp.enums.Permissions;
import com.qustodio.qustodioapp.utils.AndroidActions;
import org.testng.Assert;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class EnableQustodioPage extends AndroidActions{

	AndroidDriver driver;

	public EnableQustodioPage(AndroidDriver driver)
	{
		super(driver);
		this.driver =driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.qustodio.qustodioapp:id/closeButton")
	private WebElement allowButton;

	@AndroidFindBy(id = "com.android.settings:id/collapsing_toolbar")
	private WebElement androidSettingsLayout;

	@AndroidFindBy(xpath = "//*[@resource-id='com.android.settings:id/switchWidget' or @resource-id='android:id/switch_widget']")
	private WebElement switchWidget;

	@AndroidFindBy(xpath = "//*[@resource-id='com.android.settings:id/permission_enable_allow_button' or @resource-id='com.android.settings:id/allow_button' or @resource-id='com.android.permissioncontroller:id/permission_allow_button' or @resource-id='android:id/button1']")
	private WebElement permissionEnableAllowButton;

	@AndroidFindBy(id = "android:id/button2")
	private WebElement gotItButton;

	@AndroidFindBy(id = "com.qustodio.qustodioapp:id/titleText")
	private WebElement processTitle;

	@AndroidFindBy(id = "com.qustodio.qustodioapp:id/secondaryButton")
	private WebElement notToMonitorThisDeviceButton;

	@AndroidFindBy(id = "com.android.permissioncontroller:id/permission_allow_foreground_only_button")
	private WebElement permissionWhileUsingTheAppButton;

	@AndroidFindBy(id = "com.android.settings:id/action_button")
	private WebElement activateAdminInThisDeviceButton;

	@AndroidFindBy(id = "com.qustodio.qustodioapp:id/deviceProtectedButton")
	private WebElement deviceProtectedButton;

	@AndroidFindBy(id = "com.qustodio.qustodioapp:id/descriptionText")
	private WebElement processDescription;

	public void enableQustodio(Permissions permissions)
	{
		if (permissions == Permissions.ACCESSIBILITY_ON) {
			allowPermission(Processes.ALLOW_ACCESSIBILITY);
		} else if (permissions == Permissions.ACCESSIBILITY_OFF) {
			click(notToMonitorThisDeviceButton);
		}
		allowPermission(Processes.ALLOW_USAGE_TRACKING);
		allowPermission(Processes.ALLOW_NOTIFICATION_ACCESS);
		allowPermission(Processes.ALLOW_APP_DISPLAY);
		allowPermission(Processes.ALLOW_PERMISSIONS);
		//allowPermission(Processes.ACTIVATE_DEVICE_ADMIN);
		//click(deviceProtectedButton);
	}

	public void allowPermission(Processes process) {
		if (isProperProcess(process)) {
			click(allowButton);

			switch (process) {
				case ALLOW_PERMISSIONS:
					click(permissionEnableAllowButton);
					click(permissionWhileUsingTheAppButton);
					click(permissionEnableAllowButton);
					click(permissionEnableAllowButton);
					break;
				case ACTIVATE_DEVICE_ADMIN:
					click(activateAdminInThisDeviceButton);
					break;
				default:
					waitFor(ExpectedConditions.visibilityOf(androidSettingsLayout));
					clickElementByNames("Qustodio Kids", "com.qustodio.qustodioapp");
					click(switchWidget);

					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0)); // Desactivar el tiempo de espera implícito
					WebDriverWait shortWait = new WebDriverWait(driver, Duration.of(1, ChronoUnit.SECONDS));

					try {
						WebElement element = shortWait.until(ExpectedConditions.visibilityOf(permissionEnableAllowButton));
						click(element);
					} catch (TimeoutException ignored) {
					}
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Desactivar el tiempo de espera implícito

					break;
			}
		}
	}

	public boolean isProperProcess(Processes process) {
		waitFor(ExpectedConditions.visibilityOf(processTitle));
		Assert.assertEquals(process.getLabel(), processTitle.getText());
		Assert.assertEquals(process.getDescription(), processDescription.getText());
		return true;
	}

}
