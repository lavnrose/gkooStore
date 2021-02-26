package gkooCosmeticAgency;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import agencyBrandEntities.MassItemEcco;
import agencyEntities.AgentEcco;
import agencyEntities.BaseItemCosmetic;
import agencyEntities.MassItem;
import coupang.CoupangApi;
import factoryExcel.Cafe24;
import util.Formatter;
import util.GrobalDefined;

public class AgentEccoStarter {
private static final Logger LOGGER = LogManager.getLogger(AgentEccoStarter.class);
    
    public final static String BRAND_HOMEPAGE_URL = "https://www.ecco-verde.de/";
    public static final String BRAND_NAME_KOR = "벨레다";
    public static final String BRAND_NAME_DE = "weleda";
    public static final String DIR_BRAND = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/화장품/ecoverde/" + BRAND_NAME_DE;
    public final static String ITEM_CATETOTY = "/";
    public static final String DIR_BRAND_CATEGORY = DIR_BRAND + ITEM_CATETOTY;
    public static final String HTML_BRAND = DIR_BRAND_CATEGORY + "/weleda_p2.html";
    
    public static String DIR_FILEUPLOADER = BRAND_NAME_DE + ITEM_CATETOTY;
    public static String DIR_MAIN_IMAGES = DIR_BRAND_CATEGORY + "main_images/";
    public static String DIR_EXCEL_FILE = DIR_BRAND_CATEGORY;

    public static final String ITEM_CATEGORY = "";
    public static final String CATEGORY_ID_COOPANG = "";
    public static final String CATEGORY_NUMBER_CAFE24 = "153";
    
    public static void main(String[] args) throws Exception {
        LOGGER.info("AgentEccoStarter ===>>> " + BRAND_NAME_KOR);
        AgentEcco agent = getConfiguredAgent();
        
        /**
         * save manually html in local and gathering urls, item titles, downloading main images
         */
        List<MassItem> massItemList = new ArrayList<>();
        List<String> itemUrlList = agent.preprocessing(massItemList, HTML_BRAND);
        
        int size = itemUrlList.size();
        //int size = 1;
        
        for(int i=0; i<size; i++) { 
            agent.createMassItem(itemUrlList.get(i), massItemList.get(i));
            //LOGGER.info("MassItem is created:" + i);
        }
        
        List<BaseItemCosmetic> baseItemCosmeticList = new ArrayList<>();
        for(int i=0; i<size; i++) {
            MassItemEcco massItemEcco = new MassItemEcco(massItemList.get(i));
            baseItemCosmeticList.add(massItemEcco);
        }
        
        Cafe24 cafe24 = new Cafe24(BRAND_NAME_KOR, CATEGORY_NUMBER_CAFE24, baseItemCosmeticList);
        cafe24.createCsvFileCosmetic(DIR_EXCEL_FILE);
        
        //coupang api
        for(BaseItemCosmetic itemCosmetic : baseItemCosmeticList) {
            int originalPrice = Integer.valueOf(itemCosmetic.getPriceWonString());
            int salePrice = Integer.valueOf(itemCosmetic.getPriceWonString());
            String contentHtml = itemCosmetic.getItemFullDescriptionKOR();
            String mainImageName = Formatter.abbreviateStringLeft(itemCosmetic.getMassItem().getMainImageName(), 50);
            String displayProductName = itemCosmetic.getItemFullnameWithPrefix();
            String brand = itemCosmetic.getMassItem().getBrandNameDE();
            
            CoupangApi.createProductCosmetic(GrobalDefined.categoryCodeCoopang.get(""), 
                    originalPrice, salePrice, contentHtml, mainImageName, displayProductName, brand, DIR_FILEUPLOADER);
            LOGGER.info("product is create by coupang api:" + displayProductName);
        }
        
        LOGGER.info("A mission end <<<=== ");
    }
    
    public static AgentEcco getConfiguredAgent() {
        AgentEcco agent = new AgentEcco(BRAND_NAME_DE, BRAND_NAME_KOR, DIR_MAIN_IMAGES, DIR_FILEUPLOADER);
        return agent;
    }
}
