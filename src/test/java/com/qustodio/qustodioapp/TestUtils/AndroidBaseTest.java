package com.qustodio.qustodioapp.TestUtils;

import io.appium.java_client.remote.MobileCapabilityType;
import com.qustodio.qustodioapp.pageObjects.android.EnableQustodioPage;
import com.qustodio.qustodioapp.pageObjects.android.OnboardingPage;
import com.qustodio.qustodioapp.pageObjects.android.ProtectDevicePage;
import org.testng.annotations.BeforeClass;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import com.qustodio.qustodioapp.utils.AppiumUtils;
import org.testng.annotations.AfterClass;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class AndroidBaseTest extends AppiumUtils{

	public AndroidDriver driver;
	public AppiumDriverLocalService service;
	public OnboardingPage onboardingPage;
	public ProtectDevicePage protectDevicePage;
	public EnableQustodioPage enableQustodioPage;
	
	@BeforeClass(alwaysRun=true)
	public void ConfigureAppium() throws IOException
	{
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main/resources//data.properties");
		String ipAddress = System.getProperty("ipAddress")!=null ? System.getProperty("ipAddress") : prop.getProperty("ipAddress");
		System.out.println(ipAddress);
		prop.load(fis);
		String port = prop.getProperty("port");
			
		service = startAppiumServer(ipAddress,Integer.parseInt(port));

			UiAutomator2Options options = new UiAutomator2Options();
			options.setDeviceName(prop.getProperty("AndroidDeviceNames"));
			options.setApp(System.getProperty("user.dir")+"//src//main//resources//com.qustodio.qustodioapp_180.65.1.2-family.apk");
			options.setAppPackage("com.qustodio.qustodioapp");
			options.setAppActivity("ui.splash.SplashScreenActivity");
			options.setCapability(MobileCapabilityType.FULL_RESET, true);

			driver = new AndroidDriver(service.getUrl(), options);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			onboardingPage = new OnboardingPage(driver);
			protectDevicePage = new ProtectDevicePage(driver);
			enableQustodioPage = new EnableQustodioPage(driver);
	}

	
	@AfterClass(alwaysRun=true)
	public void tearDown() throws IOException {
		ProcessBuilder adbProcessBuilder = new ProcessBuilder("adb", "shell", "am", "force-stop", "com.qustodio.qustodioapp");
		Process adbProcess = adbProcessBuilder.start();
		try {
			adbProcess.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.removeApp("com.qustodio.qustodioapp");
		driver.quit();
        service.stop();
	}


}
