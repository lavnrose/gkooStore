package gkooModeAgency;

import java.util.ArrayList;
import java.util.Arrays;
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
    public static String DIR_BRAND = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/의류/" + BRAND_NAME_DE;
    public final static String ITEM_CATETOTY = "/men/strappy-sandals/";
    public static String DIR_BRAND_CATEGORY = DIR_BRAND + ITEM_CATETOTY;
    public static String HTML_BRAND = DIR_BRAND_CATEGORY + "/men_strappy-sandals.html";
    
    public static String DIR_FILEUPLOADER = BRAND_NAME_DE + ITEM_CATETOTY;
    public static String DIR_MAIN_IMAGES = DIR_BRAND_CATEGORY + "main_images/";
    public static String DIR_EXCEL_FILE = DIR_BRAND_CATEGORY;
    
    public static final int DELIVERY_FEE= 8000;
    public static Gender CATEGORY_GENDER = Gender.MALE;
    public static final String CATEGORY_ID_SMARTSTORE = "50000789";
    public static final String CATEGORY_NUMBER_CAFE24 = "300";
    private final static String COUPANG_CATEGORY_CODE = "샌들"; //GrobalDefined.categoryCodeCoopang

    private static final String [] ITEM_SIZE_WIDTH = {"regular", "narrow"};
    private static final String [] ITEM_SIZE_EU_WOMEN = {"35(225mm)", "36(230mm)", "37(240mm)", "38(245mm)", "39(250mm)","40(260mm)","41(265mm)","42(270mm)","43(280mm)"};
    private static final String [] ITEM_SIZE_EU_MEN = {"39(250mm)","40(260mm)","41(265mm)","42(270mm)","43(280mm)","44(285mm)","45(290mm)","46(300mm)","47(305mm)"};
    private static final String ITEM_SIZE_COUPANG_WOMEN = "250";
    
    private static final String [] ITEM_SIZE_PRICE = {"0", "0", "0", "0", "0"};
    private static final String [] ITEM_SIZE_STOCK = {"10", "10", "10", "10", "10", "10", "10", "10", "10"};
    
    public static final List<String> ITEM_SIZE_LIST = Arrays.asList(ITEM_SIZE_EU_MEN);
    public static final List<String> ITEM_SIZE_PRICE_LIST = Arrays.asList(ITEM_SIZE_PRICE);
    public static final List<String> ITEM_SIZE_STOCK_LIST = Arrays.asList(ITEM_SIZE_STOCK);

    //public static List<String> itemSameTitleTester = new ArrayList<>();
    
    public static void main(String[] args) throws Exception {
        LOGGER.info("A mission of agentBirken starts ===>>> ");
        AgentBirken agent = getConfiguredAgent();
        /**
         * save manually html in local and gathering urls, item titles, downloading main images
         */
        List<MassItem> massItemList = new ArrayList<>();
        List<String> itemUrlList = agent.preprocessing(massItemList, HTML_BRAND);
        
        for(int i=0; i<itemUrlList.size(); i++) { 
        //for(int i=0; i<1; i++) {
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
        
        Cafe24 cafe24 = new Cafe24(baseItemList, BRAND_NAME_KOR, CATEGORY_NUMBER_CAFE24, DIR_FILEUPLOADER);
        cafe24.createCsvFileMode(DIR_EXCEL_FILE);
        
        SmartStore smartStore = new SmartStore(baseItemList, CATEGORY_ID_SMARTSTORE, BRAND_NAME_KOR);
        smartStore.createExcelMode(DIR_EXCEL_FILE);
        
        //CoupangApi CoupangApi = new CoupangApi();
        //CoupangApi.createProducts(baseItemList, GrobalDefined.categoryCodeCoopang.get(COUPANG_CATEGORY_CODE), DIR_FILEUPLOADER, ITEM_SIZE_COUPANG_WOMEN);
        LOGGER.info("A mission end <<<=== ");
    }
    
    public static AgentBirken getConfiguredAgent() {
        AgentBirken agent = new AgentBirken(BRAND_NAME_DE, BRAND_NAME_KOR, BRAND_HOMEPAGE_URL, DIR_MAIN_IMAGES,
                ITEM_SIZE_LIST, ITEM_SIZE_PRICE_LIST, ITEM_SIZE_STOCK_LIST, CATEGORY_GENDER, DELIVERY_FEE);
        return agent;
    }
}
