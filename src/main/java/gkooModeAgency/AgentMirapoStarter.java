package gkooModeAgency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import agencyBrandEntities.MassItemBirken;
import agencyBrandEntities.MassItemMirapo;
import agencyEntities.AgentMirapo;
import agencyEntities.BaseItem;
import agencyEntities.MassItem;
import coupang.CoupangApi;
import factoryExcel.Cafe24;
import factoryExcel.SmartStore;
import util.GrobalDefined;
import util.GrobalDefined.Gender;

public class AgentMirapoStarter {
 private static final Logger LOGGER = LogManager.getLogger(AgentBirkenStarter.class);
    
    public final static String BRAND_HOMEPAGE_URL = "https://www.mirapodo.de";
    public final static String BRAND_NAME_KOR = "가버";
    public final static String BRAND_NAME_DE = "gabor";
    public final static String DIR_BRAND = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/의류/mirapodo/" + BRAND_NAME_DE;
    public final static String ITEM_CATEGORY = "klassische-sandalen";
    public final static String DIR_ITEM_CATEGORY = "/" + ITEM_CATEGORY + "/";
    public static final String DIR_BRAND_CATEGORY = DIR_BRAND + DIR_ITEM_CATEGORY;
    public static final String HTML_BRAND = DIR_BRAND_CATEGORY + "/klassische-sandalen-2.html";
    
    public static String DIR_FILEUPLOADER = "mirapodo/" + BRAND_NAME_DE + DIR_ITEM_CATEGORY;
    public static String DIR_MAIN_IMAGES = DIR_BRAND_CATEGORY + "main_images/";
    public static String DIR_EXCEL_FILE = DIR_BRAND_CATEGORY;
    
    public static final int DELIVERY_FEE= 10000;
    public static Gender CATEGORY_GENDER = Gender.FEMALE;
    public static final String CATEGORY_ID_SMARTSTORE = "50003842";
    public static final String CATEGORY_NUMBER_CAFE24 = "308";
    //private final static String COUPANG_CATEGORY_CODE = "남성샌들"; //GrobalDefined.categoryCodeCoopang

    private static final String [] ITEM_SIZE_EU_WOMEN = {"35(223mm)", "35.5(226mm)", "36(230mm)", "37(236mm)", "37.5(240mm)", "38(243mm)", 
            "38.5(246mm)", "39(250mm)","40(256mm)", "40.5(260mm)", "41(263mm)","42(270mm)", "42.5(273mm)" ,"43(276mm)","44(283mm)"};
    private static final String [] ITEM_SIZE_EU_MEN = {"39(250mm)","40(260mm)","41(265mm)","42(270mm)","43(280mm)","44(285mm)","45(290mm)","46(300mm)","47(305mm)"};
    private static final String ITEM_SIZE_EU_MEN_LIST_COUPANG = "250,260,265,270,280,285,290";
    private static final String ITEM_SIZE_EU_WOMEN_LIST = "225,230,240,245,250,260,265";
    
    private static final String [] ITEM_SIZE_PRICE = {"0", "0", "0", "0", "0"};
    private static final String [] ITEM_SIZE_STOCK = {"10", "10", "10", "10", "10", "10", "10", "10", "10"};
    
    public static final List<String> ITEM_SIZE_LIST = Arrays.asList(ITEM_SIZE_EU_MEN);
    public static final List<String> ITEM_SIZE_PRICE_LIST = Arrays.asList(ITEM_SIZE_PRICE);
    public static final List<String> ITEM_SIZE_STOCK_LIST = Arrays.asList(ITEM_SIZE_STOCK);
    
    public static void main(String[] args) throws Exception {
        LOGGER.info("A mission of agentBirken starts ===>>> ");
        AgentMirapo agent = getConfiguredAgent();
        /**
         * save manually html in local and gathering urls, item titles, downloading main images
         */
        List<MassItem> massItemList = new ArrayList<>();
        List<String> itemUrlList = agent.preprocessing(massItemList, HTML_BRAND);
        
        //for(int i=0; i<itemUrlList.size(); i++) { 
        //for(int i=0; i<10; i++) {
            //agent.createMassItem(itemUrlList.get(i), massItemList.get(i));
            //LOGGER.info("MassItem is created:" + i);
        //}
        
        IntStream.range(0, itemUrlList.size())
            .forEach(index -> agent.createMassItem(itemUrlList.get(index), massItemList.get(index))); 
        
        //IntStream.range(0, 10)
        //    .forEach(index -> agent.createMassItem(itemUrlList.get(index), massItemList.get(index))); 
        
        List<BaseItem> baseItemList = new ArrayList<>();
        for(int i=0; i<massItemList.size(); i++) {
        //for(int i=0; i<10; i++) {
            //MassItemMode massItemLando = new MassItemMode(massItem);
            MassItemMirapo massItemLando = new MassItemMirapo(massItemList.get(i));
            baseItemList.add(massItemLando);
        }
        
        for(int i=0; i<massItemList.size(); i++) {
            //for(int i=0; i<10; i++) {
                //MassItemMode massItemLando = new MassItemMode(massItem);
                MassItemMirapo massItemLando = new MassItemMirapo(massItemList.get(i));
                baseItemList.add(massItemLando);
        }
        
        //massItemList.stream().forEach(massItem -> baseItemList.add(new MassItemMirapo(massItem)));
        //massItemList.stream().limit(10).forEach(massItem -> baseItemList.add(new MassItemMirapo(massItem)));
        
        Cafe24 cafe24 = new Cafe24(baseItemList, BRAND_NAME_KOR, CATEGORY_NUMBER_CAFE24, DIR_FILEUPLOADER);
        cafe24.createCsvFileMode(DIR_EXCEL_FILE);
        
        SmartStore smartStore = new SmartStore(baseItemList, CATEGORY_ID_SMARTSTORE, BRAND_NAME_KOR);
        smartStore.createExcelMode(DIR_EXCEL_FILE);
        
        //CoupangApi CoupangApi = new CoupangApi();
        //CoupangApi.createProducts(baseItemList, GrobalDefined.categoryCodeCoopang.get(COUPANG_CATEGORY_CODE), DIR_FILEUPLOADER, ITEM_SIZE_EU_MEN_LIST_COUPANG);
        LOGGER.info("A mission end <<<=== ");
    }
    
    public static AgentMirapo getConfiguredAgent() {
        AgentMirapo agent = new AgentMirapo(BRAND_NAME_DE, BRAND_NAME_KOR, BRAND_HOMEPAGE_URL, DIR_MAIN_IMAGES,
                ITEM_SIZE_LIST, ITEM_SIZE_PRICE_LIST, ITEM_SIZE_STOCK_LIST, CATEGORY_GENDER, DELIVERY_FEE);
        return agent;
    }
}
