package org.example.oopjavagroupproject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.spi.ResourceBundleProvider;

public class PropertiesBundleProvider implements ResourceBundleProvider {
    @Override
    public ResourceBundle getBundle(String baseName, Locale locale) {
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, "properties");
        try (InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(resourceName)) {
            if (stream != null) {
                return new PropertyResourceBundle(new InputStreamReader(stream, StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            // Ignore
        }
        return null;
    }

    private String toBundleName(String baseName, Locale locale) {
        if (locale.equals(Locale.ROOT)) {
            return baseName;
        }
        String language = locale.getLanguage();
        String country = locale.getCountry();
        if (!language.isEmpty() && !country.isEmpty()) {
            return baseName + "_" + language + "_" + country;
        }
        if (!language.isEmpty()) {
            return baseName + "_" + language;
        }
        return baseName;
    }

    private String toResourceName(String bundleName, String suffix) {
        return bundleName.replace('.', '/') + "." + suffix;
    }
}
