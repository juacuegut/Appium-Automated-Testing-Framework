package com.qustodio.qustodioapp;

import com.qustodio.qustodioapp.TestUtils.AndroidBaseTest;
import com.qustodio.qustodioapp.enums.Permissions;
import com.qustodio.qustodioapp.enums.Processes;
import com.qustodio.qustodioapp.testData.DataObject;
import com.qustodio.qustodioapp.testData.TestData;
import org.testng.annotations.Test;

public class QustodioKidsAppTest extends AndroidBaseTest {

    @Test(dataProvider = "testData", dataProviderClass = TestData.class)
    public void configureDeviceTest(DataObject data) {
        onboardingPage.performLogIn(data.getEmail(), data.getPassword());
        protectDevicePage.protectDevice(data.getDeviceName(), data.getWhoUsesThisDevice());
        enableQustodioPage.enableQustodio(Permissions.ACCESSIBILITY_ON);
        //enableQustodioPage.isProperProcess(Processes.ALL_DONE_SCREEN);
    }
}
