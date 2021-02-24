package translator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.v3.GlossaryName;
import com.google.cloud.translate.v3.LocationName;
import com.google.cloud.translate.v3.TranslateTextGlossaryConfig;
import com.google.cloud.translate.v3.TranslateTextRequest;
import com.google.cloud.translate.v3.TranslateTextResponse;
import com.google.cloud.translate.v3.TranslationServiceClient;
import com.google.cloud.translate.v3.TranslationServiceSettings;

public class TranslateApi {
    private static final String userDir = System.getProperty("user.dir");
    private static final String configPath = userDir + "/src/main/resources/translate.properties";

    private static String PROJECT_ID;
    private static String GLOSSARY_ID;
    private static String SOURCE_LANGUAGE;
    private static String TARGET_LANGUAGE;
    
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
    
    public static String translateTextWithGlossary(String text) throws IOException {
        configureGlossary();
        
        TranslationServiceSettings translationServiceSettings =
                TranslationServiceSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(TranslatorUtil.getCredentials()))
                    .build();
        TranslationServiceClient translationServiceClient = TranslationServiceClient.create(translationServiceSettings);
   
        
        return translateTextWithGlossary(translationServiceClient, PROJECT_ID, SOURCE_LANGUAGE, TARGET_LANGUAGE, text, GLOSSARY_ID);
    }
    
    private static void configureGlossary() {
        try (InputStream input = new FileInputStream(configPath)) {

            Properties prop = new Properties();

            prop.load(input);

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            PROJECT_ID = prop.getProperty("projectId");
            GLOSSARY_ID = prop.getProperty("glossaryId");
            SOURCE_LANGUAGE = prop.getProperty("sourceLanguage");
            TARGET_LANGUAGE = prop.getProperty("targetLanguage");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static String translateTextWithGlossary (
            TranslationServiceClient translationServiceClient,
            String projectId,
            String sourceLanguage,
            String targetLanguage,
            String text,
            String glossaryId)
            throws IOException {
        String result;
        // Initialize client that will be used to send requests. This client only needs to be created
        // once, and can be reused for multiple requests. After completing all of your requests, call
        // the "close" method on the client to safely clean up any remaining background resources.
        try (TranslationServiceClient client = translationServiceClient) {
            // Supported Locations: `global`, [glossary location], or [model location]
            // Glossaries must be hosted in `us-central1`
            // Custom Models must use the same location as your model. (us-central1)
            String location = "us-central1";
            LocationName parent = LocationName.of(projectId, location);

            GlossaryName glossaryName = GlossaryName.of(projectId, location, glossaryId);
            TranslateTextGlossaryConfig glossaryConfig =
                TranslateTextGlossaryConfig.newBuilder().setGlossary(glossaryName.toString()).build();

            // Supported Mime Types: https://cloud.google.com/translate/docs/supported-formats
            TranslateTextRequest request =
                TranslateTextRequest.newBuilder()
                    .setParent(parent.toString())
                    .setMimeType("text/plain")
                    .setSourceLanguageCode(sourceLanguage)
                    .setTargetLanguageCode(targetLanguage)
                    .addContents(text)
                    .setGlossaryConfig(glossaryConfig)
                    .build();

            TranslateTextResponse response = client.translateText(request);
            StringBuilder stbd = new StringBuilder();
            // Display the translation for each input text provided
            for (com.google.cloud.translate.v3.Translation translation : response.getGlossaryTranslationsList()) {
                System.out.printf("Translated text: %s\n", translation.getTranslatedText());
                stbd.append(translation.getTranslatedText());
            }
            
            result = stbd.toString();
          }
          return result;
    }
}
