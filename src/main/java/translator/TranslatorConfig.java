package translator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.auth.Credentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

public class TranslatorConfig {
    private static final Logger LOGGER = LogManager.getLogger(TranslatorConfig.class);

    private static final String userDir = System.getProperty("user.dir");
    private static final String CONFIG_PATH = userDir + "/src/main/resources/translate.properties";

    public static Credentials getCredentials() throws FileNotFoundException, IOException {
        return ServiceAccountCredentials
                .fromStream(new FileInputStream(getCredentialFile()));
    }
    
    private static String getCredentialFile() {
        Properties prop = getProperties();
        return prop.getProperty("credentialFile"); 
    }
    
    public static ConfigGlossary getConfigureGlossary() {
        Properties prop = getProperties();
        ConfigGlossary glossary = new ConfigGlossary(prop.getProperty("projectId"), prop.getProperty("glossaryId"),
                prop.getProperty("sourceLanguage"), prop.getProperty("targetLanguage"), prop.getProperty("inputUrl"));
        
        return glossary;
    }
    
    private static Properties getProperties() {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_PATH)) {
            prop.load(input);
        } catch (IOException ex) {
            LOGGER.error(ex);
        }
        return prop;
    }
}
