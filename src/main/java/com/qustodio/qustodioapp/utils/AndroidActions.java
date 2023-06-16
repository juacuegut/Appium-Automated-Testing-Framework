package com.qustodio.qustodioapp.utils;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.List;

public class AndroidActions extends AppiumUtils{
	
	AndroidDriver driver;

	@AndroidFindBy(xpath = "//android.widget.TextView")
	private List<WebElement> textElements;
	
	public AndroidActions(AndroidDriver driver)
	{
	
		this.driver = driver;
	}
	
	public void longPressAction(WebElement ele)
	{
		((JavascriptExecutor)driver).executeScript("mobile: longClickGesture",
				ImmutableMap.of("elementId",((RemoteWebElement)ele).getId(),
						"duration",2000));
	}
	

	public void scrollToEndAction()
	{
		boolean canScrollMore;
		do
		{
		 canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
			    "left", 100, "top", 100, "width", 200, "height", 200,
			    "direction", "down",
			    "percent", 3.0
			    
			));
		}while(canScrollMore);
	}

	public void scrollToText(String... texts) {
		StringBuilder combinedTextSelector = new StringBuilder();

		for (int i = 0; i < texts.length; i++) {
			String text = texts[i];
			String textSelector = text;

			if (i > 0) {
				combinedTextSelector.append("|");
			}

			combinedTextSelector.append(textSelector);
		}

		String scrollableText = String.format("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textMatches(\"%s\"))", combinedTextSelector.toString());
		driver.findElement(AppiumBy.androidUIAutomator(scrollableText));
	}


	public void swipeAction(WebElement ele,String direction) {
		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
				"elementId", ((RemoteWebElement)ele).getId(),
			 
			    "direction", direction,
			    "percent", 0.75
			));
	}

	public void click(WebElement element) {
		waitFor(ExpectedConditions.visibilityOf(element));
		waitFor(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}

	public void clickElementByNames(String... names) {
		scrollToText(names);
		WebElement qustodioKidsApp = returnElementByText(textElements, names);
		click(qustodioKidsApp);
	}


	public void clickWithSpecialTimeInterval(WebElement element, Integer secondsToWait) {
		waitFor(ExpectedConditions.visibilityOf(element), secondsToWait);
		element.click();
	}

	protected void sendKeys(WebElement element, String value) {
		waitFor(ExpectedConditions.elementToBeClickable(element));
		element.clear();
		element.sendKeys(value);
	}

	public WebElement returnElementByText(List<WebElement> listOfWebElements, String textToSearch) {
		for (WebElement element : listOfWebElements) {
			waitFor(ExpectedConditions.visibilityOf(element));
			String text = element.getText();
			if (text.contains(textToSearch)) {
				return element;
			}
		}
		return null;
	}

	public WebElement returnElementByText(List<WebElement> listOfWebElements, String... textsToSearch) {
		for (WebElement element : listOfWebElements) {
			waitFor(ExpectedConditions.visibilityOf(element));
			String text = element.getText();
			for (String textToSearch : textsToSearch) {
				if (text.contains(textToSearch)) {
					return element;
				}
			}
		}
		return null;
	}

	protected void waitFor(ExpectedCondition<WebElement> condition) {
		Wait<AndroidDriver> wait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofMillis(500))
				.ignoring(org.openqa.selenium.StaleElementReferenceException.class);

		wait.until(condition);
	}

	protected void waitFor(ExpectedCondition<WebElement> condition, Integer secondsToWait) {
		Wait<AndroidDriver> wait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(secondsToWait))
				.pollingEvery(Duration.ofMillis(500))
				.ignoring(org.openqa.selenium.StaleElementReferenceException.class);

		wait.until(condition);
	}

}
