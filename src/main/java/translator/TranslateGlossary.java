package translator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.translate.v3.GlossaryName;
import com.google.cloud.translate.v3.LocationName;
import com.google.cloud.translate.v3.TranslateTextGlossaryConfig;
import com.google.cloud.translate.v3.TranslateTextRequest;
import com.google.cloud.translate.v3.TranslateTextResponse;
import com.google.cloud.translate.v3.Translation;
import com.google.cloud.translate.v3.TranslationServiceClient;
import com.google.cloud.translate.v3.TranslationServiceSettings;

public class TranslateGlossary {
    
    public static String translateTextWithGlossary(String text) throws IOException {        
        TranslationServiceSettings translationServiceSettings =
                TranslationServiceSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(TranslatorConfig.getCredentials()))
                    .build();
        TranslationServiceClient translationServiceClient = TranslationServiceClient.create(translationServiceSettings);
   
        ConfigGlossary config = TranslatorConfig.getConfigureGlossary();
        
        List<String> languageCodes = new ArrayList<>();
        languageCodes.add(config.getSourceLanguage());
        languageCodes.add(config.getTargetLanguage());
        
        return translateTextWithGlossary(translationServiceClient, config.getProjectId(), config.getSourceLanguage(), config.getTargetLanguage(), text, config.getGlossaryId());
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
            for (Translation translation : response.getGlossaryTranslationsList()) {
                //System.out.printf("Translated text: %s\n", translation.getTranslatedText());
                stbd.append(translation.getTranslatedText());
            }
            
            result = stbd.toString();
          }
          return result;
    }
}
