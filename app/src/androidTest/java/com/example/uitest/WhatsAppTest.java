package com.example.uitest;
import android.content.Context;
import android.content.Intent;
import androidx.test.InstrumentationRegistry;
import androidx.test.filters.SdkSuppress;
import androidx.test.runner.AndroidJUnit4;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import androidx.test.core.app.ApplicationProvider;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class WhatsAppTest {

    private static final String WHATSAPP_PACKAGE_NAME = "com.whatsapp";
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final int TIMEOUT = 3000;
    private UiDevice device;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        device.pressHome();

        // Wait for launcher
        final String launcherPackage = device.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // Launch the app
        Context context = ApplicationProvider.getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(WHATSAPP_PACKAGE_NAME);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(WHATSAPP_PACKAGE_NAME).depth(0)),
                LAUNCH_TIMEOUT);
    }

    @Test
    public void performGivenScenario() throws UiObjectNotFoundException {

        UiObject icon = device.findObject(new UiSelector().resourceId("com.whatsapp:id/menuitem_search"));
        icon.waitForExists(TIMEOUT);
        icon.click();

        UiObject editText = device.findObject(new UiSelector().resourceId("com.whatsapp:id/search_src_text"));
        editText.waitForExists(TIMEOUT);
        editText.setText("ovi");
        device.pressEnter();

        UiObject relativeLayout = device.findObject(new UiSelector().resourceId("com.whatsapp:id/contact_row_container"));
        relativeLayout.waitForExists(TIMEOUT);
        relativeLayout.click();

        UiObject messageInputField = device.findObject(new UiSelector().resourceId("com.whatsapp:id/entry"));
        messageInputField.waitForExists(TIMEOUT);
        messageInputField.setText("ki koren mia");

//        UiObject sendButton = device.findObject(new UiSelector().resourceId("com.whatsapp:id/send"));
//        sendButton.waitForExists(TIMEOUT);
//        sendButton.click();
    }
}
