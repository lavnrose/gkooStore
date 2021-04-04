package gkooCosmeticAgency;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import agencyBrandEntities.MassItemConverter;
import agencyEntities.BaseItemCosmetic;
import agencyEntities.MassItem;
import coupang.CoupangApi;
import factoryExcel.Cafe24;
import util.Formatter;
import util.GrobalDefined;


/**
 * 
 * 1. make image and edit imageName
 * 2. write ingredientsBoard and descriptionBoard_DE
 * 3. write below brandInput and productInput
 * 4. run AgentManualReady -> image upload in cafe24
 * 5. run AgentManualReady -> translation -> edit, add descriptionBoard  
 * 6. run AgentManual
 * 
 * @author sanghuncho
 *
 */
public class AgentManual {
    private static final Logger LOGGER = LogManager.getLogger(AgentManual.class);

    //##### Brand Input
    public final static String BRAND_NAME_KOR = "허바신";
    public final static String BRAND_NAME_DE = "herbacin";
    //##### Product Input
    //#####
    private final static String PRODUCT_NAME_KOR = BRAND_NAME_KOR + " 인텐시브 크림";// 용량 X
    public  final static String PRODUCT_IMAGE_NAME = "Kamille_Intensiv_Pflegecreme_75ml"; // name + volume
    private final static double PRODUCT_PRICE = 3.99;
    private final static String PRODUCT_VOLUME = "75ml";
    private final static int    PRODUCT_EXTRA_FEE = 0;
    private final static String PRODUCT_USAGE = "15"; //GrobalDefined.categoryUsageManual
    private final static String PRODUCT_HIDDEN_URL = "https://www.pharmeo.de/10345220-herbacin-kamille-intensiv-pflegecreme-tube.html";
    private final static String COUPANG_CATEGORY_CODE = "데이크림"; //GrobalDefined.categoryCodeCoopang
    public static final String CATEGORY_NUMBER_CAFE24 = "";

    //#####
    //##### Product Input   
    
    public static String DIR_BRAND = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/cosmetic/" + BRAND_NAME_KOR;
    public static String DIR_BRAND_CATEGORY = DIR_BRAND + "/";
    public static String DIR_MAIN_IMAGES = DIR_BRAND_CATEGORY + "main_images/";
    public static String DIR_EXCEL_FILE = DIR_BRAND_CATEGORY;

    public static final String ITEM_CATEGORY = "/";
    public static String DIR_FILEUPLOADER = BRAND_NAME_DE + ITEM_CATEGORY;
    public static final String CATEGORY_ID_SMARTSTORE = "";
    public static final String CATEGORY_ID_COOPANG = "";
    private static final String userDir = System.getProperty("user.dir");
    private static final String descriptionPath = userDir + "/src/main/resources/cosmetic/descriptionBoard.txt";
    private static final String ingedientsPath = userDir + "/src/main/resources/cosmetic/ingredientsBoard.txt";
    
    public static void main(String[] args) throws Exception {
        LOGGER.info("AgentManual starts ===>>> " + BRAND_NAME_KOR);
        
        MassItem massItem = new MassItem(PRODUCT_NAME_KOR, PRODUCT_IMAGE_NAME, PRODUCT_VOLUME, PRODUCT_EXTRA_FEE, PRODUCT_USAGE, DIR_FILEUPLOADER);
        massItem.setItemPriceEuro(PRODUCT_PRICE);
        massItem.setItemDescription(readDescriptionBoard());
        massItem.setItemIngredients(readIngredientsBoard());
        massItem.setItemUrl(PRODUCT_HIDDEN_URL);
        
        List<BaseItemCosmetic> baseItemCosmeticList = new ArrayList<>();
        MassItemConverter massItemConverter = new MassItemConverter(massItem);
        baseItemCosmeticList.add(massItemConverter);
        
        Cafe24 cafe24 = new Cafe24(BRAND_NAME_KOR, CATEGORY_NUMBER_CAFE24, baseItemCosmeticList);
        cafe24.createCsvFileManual(AgentManual.DIR_EXCEL_FILE, PRODUCT_NAME_KOR);
        
        //coupang api
        for(BaseItemCosmetic itemCosmetic : baseItemCosmeticList) {
            int originalPrice = Integer.valueOf(itemCosmetic.getPriceWonString());
            int salePrice = Integer.valueOf(itemCosmetic.getPriceWonString());
            String contentHtml = itemCosmetic.getItemFullDescriptionManual();
            String mainImageName = Formatter.abbreviateStringLeft(itemCosmetic.getMassItem().getItemTitleDE(), 50);
            String displayProductName = itemCosmetic.getItemFullnameWithPrefix();
            String brand = itemCosmetic.getMassItem().getBrandNameDE();
            
            CoupangApi.createProductCosmetic(GrobalDefined.categoryCodeCoopang.get(COUPANG_CATEGORY_CODE), 
                    originalPrice, salePrice, contentHtml, mainImageName, displayProductName, brand, DIR_FILEUPLOADER);
            LOGGER.info("product is create by coupang api:" + displayProductName);
        }
        LOGGER.info("Image file: " + massItem.getItemTitleDE() + ".jpg");
        LOGGER.info("AgentManual is end <<<=== "  + BRAND_NAME_KOR);
    }
    
    public static String readDescriptionBoard() {
        String description = ""; 
        try { 
            description = new String(Files.readAllBytes(Paths.get(descriptionPath)));
            description = description.replace("\n", "").replace("\r", "");
        } catch (IOException e) { 
                e.printStackTrace(); 
        }

        return description;
    }
    
    public static String readIngredientsBoard() {
        String ingedients = ""; 
        try { 
            ingedients = new String(Files.readAllBytes(Paths.get(ingedientsPath)));
            ingedients = ingedients.replace("\n", "").replace("\r", "");
        } catch (IOException e) { 
                e.printStackTrace(); 
        }
        return ingedients;
    }
}