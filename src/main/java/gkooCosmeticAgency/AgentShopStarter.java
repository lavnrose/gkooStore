package gkooCosmeticAgency;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import agencyBrandEntities.MassItemShopApo;
import agencyEntities.AgentShopApo;
import agencyEntities.BaseItemCosmetic;
import agencyEntities.MassItem;
import coupang.CoupangApi;
import factoryExcel.Cafe24;
import util.GrobalDefined;
import util.MathUtil;

public class AgentShopStarter {
    private static final Logger LOGGER = LogManager.getLogger(AgentShopStarter.class);
    
    public final static String BRAND_HOMEPAGE_URL = "https://www.shop-apotheke.com/";
    public static final String BRAND_NAME_KOR = "유세린";
    public static final String BRAND_NAME_DE = "eucerin";
    public static final String DIR_BRAND = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/cosmetic/shopApo/" + BRAND_NAME_DE;
    public final static String ITEM_CATEGORY = "lotion";
    public final static String DIR_ITEM_CATEGORY = "/" + ITEM_CATEGORY + "/";
    public static final String DIR_BRAND_CATEGORY = DIR_BRAND + DIR_ITEM_CATEGORY;
    public static final String HTML_BRAND = DIR_BRAND + "/eucerin.html";
    
    public static String DIR_FILEUPLOADER = BRAND_NAME_DE + DIR_ITEM_CATEGORY;
    public static String DIR_MAIN_IMAGES = DIR_BRAND_CATEGORY + "main_images/";
    public static String DIR_EXCEL_FILE = DIR_BRAND_CATEGORY;

    public static final String CATEGORY_ID_COOPANG = "";
    public static final String CATEGORY_NUMBER_CAFE24 = "305";
    
    public static void main(String[] args) throws Exception {
        //1.
        createCafe24();
        
        //2. after 1
        //createCoupang();
        
    }
    
    private static void createCoupang() throws FileNotFoundException, IOException, CsvValidationException {
        LOGGER.info("Coupang API starts ===>>> " + BRAND_NAME_KOR);
        String fileName = DIR_BRAND_CATEGORY + "/유세린_cream_set_3_cafe24.csv";
        List<List<String>> records = new ArrayList<List<String>>();
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        }
        
        int start = 1;
        int size = records.size();
        for(int i=start; i< size; i++) {
            List<String> cosmetic = records.get(i);
            int originalPrice = getCoupangPrice(cosmetic.get(19));
            int salePrice = getCoupangPrice(cosmetic.get(22));
            String contentHtml = cosmetic.get(14);
            String mainImageName = cosmetic.get(10);
            String displayProductName = cosmetic.get(7);
            String brand = BRAND_NAME_DE;
            CoupangApi.createProductCosmetic(GrobalDefined.categoryCodeCoopang.get(""), 
                    originalPrice, salePrice, contentHtml, mainImageName, displayProductName, brand, DIR_FILEUPLOADER);
        }
        LOGGER.info("Coupang API is finished <<<=== ");
    }
    
    private static int getCoupangPrice(String price) {
        double cafe24Price = Double.valueOf(price);
        double coupangPrice = (cafe24Price/0.9);
        int ceiledProductPriceWon = MathUtil.mathCeilDigit(3, coupangPrice);
        return ceiledProductPriceWon;
    }
    
    private static void createCafe24() throws IOException {
        LOGGER.info("the csv file for cafe24 starts ===>>> " + BRAND_NAME_KOR);
        AgentShopApo agent = getConfiguredAgent();
        
        /**
         * save manually html in local and gathering urls, item titles, downloading main images
         */
        List<MassItem> massItemList = new ArrayList<>();
        List<String> itemUrlList = agent.preprocessing(massItemList, HTML_BRAND);
        
        int size = itemUrlList.size();
        //int size = 1;
        
        for(int i=0; i<size; i++) { 
            agent.createMassItem(itemUrlList.get(i), massItemList.get(i));
            LOGGER.info("MassItem is created:" + i);
        }

        List<BaseItemCosmetic> baseItemCosmeticList = new ArrayList<>();
        for(int i=0; i<size; i++) {
            MassItemShopApo massItemEcco = new MassItemShopApo(massItemList.get(i));
            baseItemCosmeticList.add(massItemEcco);
        }
        
        Cafe24 cafe24 = new Cafe24(BRAND_NAME_KOR, CATEGORY_NUMBER_CAFE24, baseItemCosmeticList);
        cafe24.createCsvFileCosmetic(DIR_EXCEL_FILE);
        LOGGER.info("the csv file for cafe24 is finished <<<=== ");
    }
    
    public static AgentShopApo getConfiguredAgent() {
        AgentShopApo agent = new AgentShopApo(BRAND_NAME_DE, BRAND_NAME_KOR, ITEM_CATEGORY, DIR_MAIN_IMAGES, DIR_FILEUPLOADER);
        agent.addItemUrl("https://www.shop-apotheke.com/beauty/13889268/eucerin-ph5-leichte-textur-lotion.htm");
        agent.addItemUrl("https://www.shop-apotheke.com/beauty/11678136/eucerin-urearepair-original-lotion-3.htm");
        agent.addItemUrl("https://www.shop-apotheke.com/beauty/3815725/eucerin-sun-sensitive-protect-lotion-extra-light-lsf-50.htm");
        agent.addItemUrl("https://www.shop-apotheke.com/beauty/13649400/eucerin-sun-photoaging-control-lotion-extra-light-lsf-50.htm");
        agent.addItemUrl("https://www.shop-apotheke.com/beauty/13649392/eucerin-sun-photoaging-control-lotion-extra-light-lsf-30.htm");
        agent.addItemUrl("https://www.shop-apotheke.com/beauty/3820169/eucerin-sun-sensitive-relief-after-lotion.htm");
        agent.addItemUrl("https://www.shop-apotheke.com/beauty/13649423/eucerin-sensitive-protect-sun-lotion-lsf-50.htm");
        agent.addItemUrl("https://www.shop-apotheke.com/beauty/11363467/eucerin-sun-sensitive-protect-kids-mineral-sun-lotion-lsf-30.htm");
        
        return agent;
    }
}
