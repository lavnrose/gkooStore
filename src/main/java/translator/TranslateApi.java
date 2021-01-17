package translator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class TranslateApi {
    private static Translate getTranslateApi() throws FileNotFoundException, IOException {
        Translate translate = TranslateOptions
                .newBuilder()
                .setCredentials(
                    ServiceAccountCredentials
                                .fromStream(new FileInputStream(
                                        "C:/Users/sanghuncho/Documents/Google_Cloud/gkoo-translator-68c45ea2878f.json")))
                .build().getService();
        return translate;
    }
    
    public static String doTranslateDEtoKor(String sentences) throws FileNotFoundException, IOException {
        Translation translation =
                getTranslateApi().translate(sentences,
                    Translate.TranslateOption.sourceLanguage("de"),
                    Translate.TranslateOption.targetLanguage("ko"),
                    Translate.TranslateOption.model("nmt"));
        return translation.getTranslatedText();
    }
    
    public static String doTranslateDEtoEng(String sentences) throws FileNotFoundException, IOException {
        Translation translation =
                getTranslateApi().translate(sentences,
                    Translate.TranslateOption.sourceLanguage("de"),
                    Translate.TranslateOption.targetLanguage("en"),
                    Translate.TranslateOption.model("nmt"));
        return translation.getTranslatedText();
    }
}
