package com.qustodio.qustodioapp.enums;

import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

@Getter
public enum Processes {
    ALLOW_ACCESSIBILITY,
    ALLOW_USAGE_TRACKING,
    ALLOW_NOTIFICATION_ACCESS,
    ALLOW_APP_DISPLAY,
    ALLOW_PERMISSIONS,
    ACTIVATE_DEVICE_ADMIN,
    ALL_DONE_SCREEN;

    private String label;
    private String description;

    private static final ResourceBundle bundle;

    static {
        String language = loadLanguageFromProperties();
        Locale locale = Locale.forLanguageTag(language);
        bundle = ResourceBundle.getBundle("processes_" + language, locale);

        for (Processes process : Processes.values()) {
            process.label = bundle.getString(process.name() + ".label");
            process.description = bundle.getString(process.name() + ".description");
        }
    }

    private static String loadLanguageFromProperties() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(System.getProperty("user.dir")+"//src//main//resources//data.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty("language");
    }
}
