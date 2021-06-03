package gkooModeAgency;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import agencyBrandEntities.ItemFeelway;
import agencyController.GooxController;
import util.Formatter;

public class AgentGooxStarter {
    private static final Logger LOGGER = LogManager.getLogger(AgentGooxStarter.class);
    public final static String BRAND_NAME_KOR = "돌체앤가바나";
    public final static String BRAND_NAME_ENG = "dolce&gabbana";
    public final static String BRAND_GENDER   = "women";
    
    public final static String ITEM_CATEGORY = "t-shirt";
    //public final static String ITEM_CATEGORY = "poloshirt";
    
    //product info
    public final static String ITEM_TITLE_KOR      = "티셔츠 12465335KI";
    public final static String ITEM_TITLE_ENG      = "t-shirt 12465335KI";
    public final static String ITEM_MODEL_NUMBER   = "12465335KI";//random number
    public final static String ITEM_ORIGIN_COUNTRY = "유럽";
    public final static String ITEM_MATERIAL       = "100% Cotton, Polyester, Elastan";
    public final static String ITEM_COLOR          = "Pink";
    public final static String ITEM_SIZE_LIST      = "XS, S, M, L, XL";
    public final static int    ITEM_PRICE_EURO     = 212;
    public final static int    ITEM_DELIVERY_PRICE = 10000;
    
    //directory
    public final static String DIR_BRAND = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/의류/" + BRAND_NAME_ENG + "/";
    public final static String DIR_CATEGOTY =  DIR_BRAND + "/" + BRAND_GENDER + "/" + ITEM_CATEGORY;
    public final static String DIR_ITEM =  BRAND_GENDER + "/" + ITEM_CATEGORY + "/" + ITEM_TITLE_ENG;
    public final static String DIR_BRAND_ITEM = DIR_BRAND + DIR_ITEM;
    public final static String DIR_CAFE24_UPLOAD = "https://moondrive81.cafe24.com/GKoo/mode" + "/" + BRAND_NAME_ENG + "/" + BRAND_GENDER + "/" + ITEM_CATEGORY;
    public static final String HTML_BRAND = DIR_BRAND_ITEM + "/" + ITEM_TITLE_ENG + ".html";
    
    public static final String CATEGORY_ID_SMARTSTORE = "50000830";
    
    public static void main(String[] args) throws Exception {
        ItemFeelway itemFeelway = getConfiguredItem();
        GooxController controller = new GooxController(itemFeelway);
        controller.createProductData();
    }
    
    private static List<String> getItemImageUrl() {
        File input = new File(AgentGooxStarter.HTML_BRAND);
        Document document = null;
        try {
            document = Jsoup.parse(input, "UTF-8", "");
        } catch (IOException e) {
        }
        Element body = document.body();
        Elements rawUnitElements = body.getElementsByClass("jss1").get(0).getElementsByTag("img");
        
        List<String> itemImageUrl = new ArrayList<>();
        
        for (int i=0; i<rawUnitElements.size(); i++) {
            Element unit = rawUnitElements.get(i);
            String rawImageUrl = unit.attr("src");
            String imageUrl = Formatter.splitAfterWord(rawImageUrl, ".jpg").get(0) + ".jpg";
            itemImageUrl.add(imageUrl);
        }

        return itemImageUrl;
    }
    
    private static ItemFeelway getConfiguredItem() {
        ItemFeelway item = new ItemFeelway();
        item.setItemBrandKor(BRAND_NAME_KOR);
        item.setItemBrandEng(BRAND_NAME_ENG);
        item.setItemBrandGender(BRAND_GENDER);
        item.setItemCategory(ITEM_CATEGORY);
        item.setItemTitleKor(ITEM_TITLE_KOR);
        item.setItemTitleEng(ITEM_TITLE_ENG);
        item.setItemModelNumber(ITEM_MODEL_NUMBER);
        item.setItemOriginCountry(ITEM_ORIGIN_COUNTRY);
        item.setItemMaterial(ITEM_MATERIAL);
        item.setItemColor(ITEM_COLOR);
        item.setDirBrandItem(DIR_BRAND_ITEM);
        item.setItemPriceEuro(ITEM_PRICE_EURO);
        item.setItemSizeList(ITEM_SIZE_LIST);
        item.setItemDeliveryPrice(ITEM_DELIVERY_PRICE);
        item.setSmartStoreCategoryId(CATEGORY_ID_SMARTSTORE);
        item.setDirItem(DIR_ITEM);
        item.setItemImageUrl(getItemImageUrl());
        return item;
    }
    
    public static final HashMap<String, String> brandNames = new HashMap<>() {
        {
            put("버버리", "burberry");
            put("디스퀘어드", "dsquared2");
            put("돌체앤가바나", "dolce&gabbana");
        }
    };
}
