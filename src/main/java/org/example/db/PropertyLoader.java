package org.example.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton class. It parses application.properties file for url, login and password for
 * database connection.
 */

public class PropertyLoader {
    /**
     * Path to property file
     */
    private static final String PROPERTY_FILE = "/application.properties";
    /**
     * Url to database from application.properties.
     */
    private String url;
    /**
     * Login to database from application.properties.
     */
    private String login;
    /**
     * Password to database from application.properties.
     */
    private String password;

    /**
     * Creates the singleton instance of {@link PropertyLoader}.
     */
    private static final PropertyLoader instance = new PropertyLoader();

    /**
     * Default constructor that calls {@link #loadProperties()} method.
     */
    private PropertyLoader() {
        loadProperties();
    }

    /**
     * Singleton get method.
     *
     * @return instance of {@link PropertyLoader}
     */
    public static PropertyLoader getInstance() {
        return instance;
    }

    /**
     * Load properties as stream from the ENV or  {@value PROPERTY_FILE}
     */
    private void loadProperties() {

        this.url = System.getenv("DB_URL");
        this.login = System.getenv("DB_USER");
        this.password = System.getenv("DB_PASSWORD");

        if (url == null || login == null || password == null) {
            loadPropertiesFromFile();
        }
    }

    private void loadPropertiesFromFile() {
        try (InputStream in = getClass().getResourceAsStream(PROPERTY_FILE)) {
            Properties properties = new Properties();
            properties.load(in);

            this.url = properties.getProperty("database.url");
            this.login = properties.getProperty("database.login");
            this.password = properties.getProperty("database.password");

        } catch (IOException e) {
            throw new RuntimeException("Error loading properties", e);
        }
    }

    /**
     * Getter method
     *
     * @return Url to database
     */
    public String getUrl() {
        return url;
    }

    /**
     * Getter method
     *
     * @return Login to database
     */
    public String getLogin() {
        return login;
    }

    /**
     * Getter method
     *
     * @return Password to database
     */
    public String getPassword() {
        return password;
    }

    /**
     * Object as a {@link String} method.
     *
     * @return {@link PropertyLoader} as string.
     */
    @Override
    public String toString() {
        return "PropertyLoader{" +
                "url='" + url + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
