package translator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.google.auth.Credentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

public class TranslatorUtil {

    public static Credentials getCredentials() throws FileNotFoundException, IOException {
        return ServiceAccountCredentials
                .fromStream(new FileInputStream(
                        "C:/Users/sanghuncho/Documents/Google_Cloud/gkoo-translator-68c45ea2878f.json"));
    }
}
