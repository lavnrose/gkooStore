package translator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.translate.v3.CreateGlossaryMetadata;
import com.google.cloud.translate.v3.CreateGlossaryRequest;
import com.google.cloud.translate.v3.GcsSource;
import com.google.cloud.translate.v3.Glossary;
import com.google.cloud.translate.v3.GlossaryInputConfig;
import com.google.cloud.translate.v3.GlossaryName;
import com.google.cloud.translate.v3.ListGlossariesRequest;
import com.google.cloud.translate.v3.LocationName;
import com.google.cloud.translate.v3.TranslationServiceClient;
import com.google.cloud.translate.v3.TranslationServiceSettings;

public class GlossaryManager {
    public static void main(String args[]) throws IOException, ExecutionException, InterruptedException {
        
        TranslationServiceSettings translationServiceSettings =
                     TranslationServiceSettings.newBuilder()
                         .setCredentialsProvider(FixedCredentialsProvider.create(TranslatorConfig.getCredentials()))
                         .build();
        TranslationServiceClient translationServiceClient = TranslationServiceClient.create(translationServiceSettings);
        
        ConfigGlossary config = TranslatorConfig.getConfigureGlossary();
        
        List<String> languageCodes = new ArrayList<>();
        languageCodes.add(config.getSourceLanguage());
        languageCodes.add(config.getTargetLanguage());
        
        createGlossary(translationServiceClient, config.getProjectId(), config.getGlossaryId(), languageCodes, config.getInputUrl());
        
        //listGlossaries(translationServiceClient, projectId);
   }
   
    
    /**
     * 
     * adding new glossary to the csv file -> upload in cloud with new glossaryId -> run this method
     * 
     * @param translationServiceClient
     * @param projectId
     * @param glossaryId
     * @param languageCodes
     * @param inputUri
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void createGlossary(TranslationServiceClient translationServiceClient, String projectId, String glossaryId, List<String> languageCodes, String inputUri)
           throws IOException, ExecutionException, InterruptedException {

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

            // Supported Languages: https://cloud.google.com/translate/docs/languages
           Glossary.LanguageCodesSet languageCodesSet =
                Glossary.LanguageCodesSet.newBuilder().addAllLanguageCodes(languageCodes).build();

            // Configure the source of the file from a GCS bucket
           GcsSource gcsSource = GcsSource.newBuilder().setInputUri(inputUri).build();
           GlossaryInputConfig inputConfig =
                GlossaryInputConfig.newBuilder().setGcsSource(gcsSource).build();

           Glossary glossary =
                Glossary.newBuilder()
                    .setName(glossaryName.toString())
                    .setLanguageCodesSet(languageCodesSet)
                    .setInputConfig(inputConfig)
                    .build();

           CreateGlossaryRequest request =
                CreateGlossaryRequest.newBuilder()
                    .setParent(parent.toString())
                    .setGlossary(glossary)
                    .build();

            // Start an asynchronous request
           OperationFuture<Glossary, CreateGlossaryMetadata> future =
                client.createGlossaryAsync(request);

           System.out.println("Waiting for operation to complete...");
           Glossary response = future.get();
           System.out.println("Created Glossary.");
           System.out.printf("Glossary name: %s\n", response.getName());
           System.out.printf("Entry count: %s\n", response.getEntryCount());
           System.out.printf("Input URI: %s\n", response.getInputConfig().getGcsSource().getInputUri());
       }
   }

   // List all the glossaries in a specified location
   public static void listGlossaries(TranslationServiceClient translationServiceClient, String projectId) throws IOException {
        // Initialize client that will be used to send requests. This client only needs to be created
        // once, and can be reused for multiple requests. After completing all of your requests, call
        // the "close" method on the client to safely clean up any remaining background resources.
        try (TranslationServiceClient client = translationServiceClient) {
          // Supported Locations: `global`, [glossary location], or [model location]
          // Glossaries must be hosted in `us-central1`
          // Custom Models must use the same location as your model. (us-central1)
          LocationName parent = LocationName.of(projectId, "us-central1");
          ListGlossariesRequest request =
              ListGlossariesRequest.newBuilder().setParent(parent.toString()).build();

          for (com.google.cloud.translate.v3.Glossary responseItem : client.listGlossaries(request).iterateAll()) {
            System.out.printf("Glossary name: %s\n", responseItem.getName());
            System.out.printf("Entry count: %s\n", responseItem.getEntryCount());
            System.out.printf("LanguageCodesSet: %s\n", responseItem.getLanguageCodesSet());
            System.out.printf(
                "Input URI: %s\n", responseItem.getInputConfig().getGcsSource().getInputUri());
          }
        }
   }
}
