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
import factoryExcel.SmartStore;
import util.GrobalDefined;
import util.MathUtil;

public class AgentShopStarter {
    private static final Logger LOGGER = LogManager.getLogger(AgentShopStarter.class);
    
    public final static String BRAND_HOMEPAGE_URL = "https://www.shop-apotheke.com/";
    public static final String BRAND_NAME_KOR = "오이보스";
    public static final String BRAND_NAME_DE = "eubos";
    public static final String DIR_BRAND = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/cosmetic/shopApo/" + BRAND_NAME_DE;
    public final static String ITEM_CATEGORY = "cream";
    public final static String DIR_ITEM_CATEGORY = "/" + ITEM_CATEGORY + "/";
    public static final String DIR_BRAND_CATEGORY = DIR_BRAND + DIR_ITEM_CATEGORY;
    public static final String HTML_BRAND = DIR_BRAND + "/eubos.html";
    
    public static String DIR_FILEUPLOADER = BRAND_NAME_DE + DIR_ITEM_CATEGORY;
    public static String DIR_MAIN_IMAGES = DIR_BRAND_CATEGORY + "main_images/";
    public static String DIR_EXCEL_FILE = DIR_BRAND_CATEGORY;

    public static final String CATEGORY_ID_COOPANG = "";
    public static final String CATEGORY_ID_SMARTSTORE = "50000440";
    public static final String CATEGORY_NUMBER_CAFE24 = "181";
    
    public static void main(String[] args) throws Exception {
        //1.
        //createCafe24();
        
        //2. Coupang API
        //createCoupang();
        
        //3. smartStore
        createSmartStore();
        
    }
    
    public static AgentShopApo getConfiguredAgent() {
        AgentShopApo agent = new AgentShopApo(BRAND_NAME_DE, BRAND_NAME_KOR, ITEM_CATEGORY, DIR_MAIN_IMAGES, DIR_FILEUPLOADER);
        agent.addItemUrl("https://www.shop-apotheke.com/beauty/4891958/eubos-creme-intensivpflege.htm");
        agent.addItemUrl("https://www.shop-apotheke.com/beauty/13649676/eubos-hyaluron-repair-filler-day-creme.htm");
        agent.addItemUrl("https://www.shop-apotheke.com/beauty/7392492/eubos-omega-3-6-9-intensivcreme.htm");
        agent.addItemUrl("https://www.shop-apotheke.com/beauty/9683532/eubos-10-urea-hydro-repair.htm");
        agent.addItemUrl("https://www.shop-apotheke.com/beauty/3447718/eubos-trockene-haut-3-urea-koerperlotion.htm");
        
        return agent;
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
        cafe24.createExcelFileCosmetic(DIR_EXCEL_FILE);
        LOGGER.info("the csv file for cafe24 is finished <<<=== ");
    }
    
    private static void createCoupang() throws FileNotFoundException, IOException, CsvValidationException {
        LOGGER.info("Coupang API starts ===>>> " + BRAND_NAME_KOR);
        String fileName = DIR_BRAND_CATEGORY + "/오이보스_cosmetic_cafe24.csv";
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
            List<String> cosmetic = records.get(i);;
            int originalPrice = Integer.valueOf(cosmetic.get(19));
            int salePrice = Integer.valueOf(cosmetic.get(22));
            String contentHtml = cosmetic.get(14);
            String mainImageName = cosmetic.get(10);
            String displayProductName = cosmetic.get(7);
            String brand = BRAND_NAME_DE;
            CoupangApi.createProductCosmetic(GrobalDefined.categoryCodeCoopang.get(""), 
                    originalPrice, salePrice, contentHtml, mainImageName, displayProductName, brand, DIR_FILEUPLOADER);
        }
        LOGGER.info("Coupang API is finished <<<=== ");
    }
    
    private static void createSmartStore() throws FileNotFoundException, IOException, CsvValidationException {
        LOGGER.info("SmartStore starts ===>>> " + BRAND_NAME_KOR);
        String fileName = DIR_BRAND_CATEGORY + "/오이보스_cosmetic_cafe24.csv";
        List<List<String>> records = new ArrayList<List<String>>();
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        }
        
        SmartStore sm = new SmartStore(CATEGORY_ID_SMARTSTORE, BRAND_NAME_DE);
        sm.createExcelCosmetic(DIR_EXCEL_FILE, records);
        LOGGER.info("Coupang API is finished <<<=== ");
    }
    
    private static int getCoupangPrice(String price) {
        double cafe24Price = Double.valueOf(price);
        double coupangPrice = (cafe24Price/0.9);
        int ceiledProductPriceWon = MathUtil.mathCeilDigit(3, coupangPrice);
        return ceiledProductPriceWon;
    }
}
