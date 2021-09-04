package gkooModeAgency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import agencyBrandEntities.MassItemBirken;
import agencyEntities.AgentBirken;
import agencyEntities.BaseItem;
import agencyEntities.MassItem;
import coupang.CoupangApi;
import factoryExcel.Cafe24;
import factoryExcel.SmartStore;
import util.GrobalDefined;
import util.GrobalDefined.Gender;

public class AgentBirkenStarter {
    private static final Logger LOGGER = LogManager.getLogger(AgentBirkenStarter.class);
    
    public final static String BRAND_HOMEPAGE_URL = "https://www.birkenstock.com/";
    public final static String BRAND_NAME_KOR = "버켄스탁";
    public final static String BRAND_NAME_DE = "birkenstock";
    public final static String DIR_BRAND = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/의류/" + BRAND_NAME_DE;
    public static final HashMap<String, String> ITEM_CATEGORY_SET = new HashMap<>() {
        {
            put("men_strappy", "strappy-sandals");
            put("men_two_strap", "two-strap-sandals");
            put("men_one_strap", "one-strap-sandals");
            
            put("women_multi_strap", "multi-strap-sandals");
            put("women_two_strap", "two-strap-sandals");
            put("women_one_strap", "one-strap-sandals");
            
            //kids
        }
    };
    public final static String ITEM_CATEGORY = ITEM_CATEGORY_SET.get("women_multi_strap");
    public final static String DIR_ITEM_CATEGORY = "/women/" + ITEM_CATEGORY + "/";
    public static final String DIR_BRAND_CATEGORY = DIR_BRAND + DIR_ITEM_CATEGORY;
    
    public static Gender CATEGORY_GENDER = Gender.FEMALE;
    public static final HashMap<String, String> HTML_BRAND_SET = new HashMap<>() {
        {
            put("men_strappy", "men_strappy-sandals.html");
            put("men_two_strap", "men_two-strap-sandals.html");
            put("men_one_strap", "men_one-strap-sandals.html");
            
            put("women_multi_strap", "women_multi-strap-sandals.html");
            put("women_two_strap", "women_two-strap-sandals.html");
            put("women_one_strap", "women_one-strap-sandals.html");
        }
    };
    public static final String HTML_BRAND = DIR_BRAND_CATEGORY + HTML_BRAND_SET.get("women_multi_strap");
    
    public static String DIR_FILEUPLOADER = BRAND_NAME_DE + DIR_ITEM_CATEGORY;
    public static String DIR_MAIN_IMAGES = DIR_BRAND_CATEGORY + "main_images/";
    public static String DIR_EXCEL_FILE = DIR_BRAND_CATEGORY;
    
    public static final int DELIVERY_FEE= 10000;
    public static final HashMap<String, String> CATEGORY_ID_SMARTSTORE_SET = new HashMap<>() {
        {
            put("남성샌들", "50000789");
            put("여성샌들", "50003842");
        }
    };
    static String categoryIdSmartStoreKey = CATEGORY_GENDER.equals(Gender.MALE) ? "남성샌들" : "여성샌들";
    public static final String CATEGORY_ID_SMARTSTORE = CATEGORY_ID_SMARTSTORE_SET.get(categoryIdSmartStoreKey);
    public static final String CATEGORY_NUMBER_CAFE24 = "300";

    private static final String [] ITEM_SIZE_WIDTH = {"regular", "narrow"};
    private static final String [] ITEM_SIZE_EU_WOMEN = {"35(225mm)", "36(230mm)", "37(240mm)", "38(245mm)", "39(250mm)","40(260mm)","41(265mm)","42(270mm)","43(280mm)"};
    private static final String [] ITEM_SIZE_EU_MEN = {"39(250mm)","40(260mm)","41(265mm)","42(270mm)","43(280mm)","44(285mm)","45(290mm)","46(300mm)","47(305mm)"};
    
    private static final String [] ITEM_SIZE_PRICE = {"0", "0", "0", "0", "0"};
    private static final String [] ITEM_SIZE_STOCK = {"10", "10", "10", "10", "10", "10", "10", "10", "10"};
    
    static String [] sizeList = CATEGORY_GENDER.equals(Gender.MALE) ? ITEM_SIZE_EU_MEN : ITEM_SIZE_EU_WOMEN;
    public static final List<String> ITEM_SIZE_LIST = Arrays.asList(sizeList);
    
    public static final List<String> ITEM_SIZE_PRICE_LIST = Arrays.asList(ITEM_SIZE_PRICE);
    public static final List<String> ITEM_SIZE_STOCK_LIST = Arrays.asList(ITEM_SIZE_STOCK);

    public static void main(String[] args) throws Exception {
        LOGGER.info("A mission of agentBirken starts ===>>> ");
        AgentBirken agent = getConfiguredAgent();
        /**
         * save manually html in local and gathering urls, item titles, downloading main images
         */
        List<MassItem> massItemList = new ArrayList<>();
        List<String> itemUrlList = agent.preprocessing(massItemList, HTML_BRAND);
        
        for(int i=0; i<itemUrlList.size(); i++) { 
       // for(int i=0; i<1; i++) {
            agent.createMassItem(itemUrlList.get(i), massItemList.get(i));
            //LOGGER.info("MassItem is created:" + i);
        }
        
        List<BaseItem> baseItemList = new ArrayList<>();
        for(int i=0; i<massItemList.size(); i++) {
        //for(int i=0; i<1; i++) {
            //MassItemMode massItemLando = new MassItemMode(massItem);
            MassItemBirken massItemLando = new MassItemBirken(massItemList.get(i));
            baseItemList.add(massItemLando);
        }
        
        //Cafe24 cafe24 = new Cafe24(baseItemList, BRAND_NAME_KOR, CATEGORY_NUMBER_CAFE24, DIR_FILEUPLOADER);
        //cafe24.createCsvFileMode(DIR_EXCEL_FILE);
        
        SmartStore smartStore = new SmartStore(baseItemList, CATEGORY_ID_SMARTSTORE, BRAND_NAME_KOR + " " + ITEM_CATEGORY);
        smartStore.createExcelBirkenstock(DIR_EXCEL_FILE);
        
//        CoupangApi CoupangApi = new CoupangApi();
//        CoupangApi.createProducts(baseItemList, GrobalDefined.categoryCodeCoopang.get(COUPANG_CATEGORY_CODE), DIR_FILEUPLOADER, ITEM_SIZE_EU_MEN_LIST_COUPANG);
        LOGGER.info("A mission end <<<=== ");
    }
    
    public static AgentBirken getConfiguredAgent() {
        AgentBirken agent = new AgentBirken(BRAND_NAME_DE, BRAND_NAME_KOR, BRAND_HOMEPAGE_URL, DIR_MAIN_IMAGES,
                ITEM_SIZE_LIST, ITEM_SIZE_PRICE_LIST, ITEM_SIZE_STOCK_LIST, CATEGORY_GENDER, DELIVERY_FEE);
        return agent;
    }
}
