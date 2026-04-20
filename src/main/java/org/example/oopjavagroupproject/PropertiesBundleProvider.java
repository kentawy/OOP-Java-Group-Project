package org.example.oopjavagroupproject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.spi.ResourceBundleProvider;

/**
 * A custom {@link ResourceBundleProvider} to ensure that .properties files are loaded with UTF-8 encoding.
 * This is crucial for correctly displaying non-ASCII characters (like Cyrillic) from resource bundles.
 * This provider is registered in the `module-info.java` file.
 *
 * @author Bohdan Dmytrenko, Bohdan Ruban, Olha Sribna
 * @version 1.0
 */
public class PropertiesBundleProvider implements ResourceBundleProvider {
    /**
     * Gets a resource bundle for the given base name and locale.
     *
     * @param baseName the base name of the resource bundle, a fully qualified class name
     * @param locale   the locale for which the resource bundle should be instantiated
     * @return the resource bundle for the given base name and locale, or {@code null} if no
     *         resource bundle is found
     */
    @Override
    public ResourceBundle getBundle(String baseName, Locale locale) {
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, "properties");
        try (InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(resourceName)) {
            if (stream != null) {
                // Read the properties file with UTF-8 encoding
                return new PropertyResourceBundle(new InputStreamReader(stream, StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            // In case of an error, fall back to the default mechanism by returning null.
        }
        return null;
    }

    /**
     * Converts the given base name and locale into a bundle name.
     *
     * @param baseName the base name of the resource bundle
     * @param locale   the locale
     * @return the bundle name
     */
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

    /**
     * Converts the given bundle name to a resource name.
     *
     * @param bundleName the bundle name
     * @param suffix     the suffix
     * @return the resource name
     */
    private String toResourceName(String bundleName, String suffix) {
        return bundleName.replace('.', '/') + "." + suffix;
    }
}
