package gkooCosmeticAgency;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import translator.TranslateGlossary;
import util.Formatter;
import util.ImageTransformer;

public class AgentManualReady {
    private static final Logger LOGGER = LogManager.getLogger(AgentManualReady.class);
    private static final String userDir = System.getProperty("user.dir");
    private static final String descriptionPath = userDir + "/src/main/resources/cosmetic/descriptionBoard_DE.txt";
    
    public static void main(String[] args) {
        doResizeImage();
        
        showTranslatedDescription();
    }
    
    private static void doResizeImage() {
        String productImageName = AgentManual.PRODUCT_IMAGE_NAME;
        String DirMainImages = AgentManual.DIR_MAIN_IMAGES;
        ImageTransformer.runWithResizingFile(productImageName, DirMainImages, 500, 500);
    }
    
    private static void showTranslatedDescription() {
        String description = ""; 
        try { 
            description = new String(Files.readAllBytes(Paths.get(descriptionPath)));
        } catch (IOException e) { 
                e.printStackTrace(); 
        }
        
        String translatedResult = "";
        try {
            //translatedResult = TranslateApi.doTranslateDEtoKor(description);
            translatedResult = TranslateGlossary.translateTextWithGlossary(description);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String formattedResult = Formatter.setLinebreakAfterPunct(translatedResult);
        //System.out.println(formattedResult);
        System.out.println(translatedResult);
    }
}
