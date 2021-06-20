package gkooModeAgency;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import agencyBrandEntities.ItemFeelway;
import agencyController.FeelwayController;

public class AgentChannelStarter {
private static final Logger LOGGER = LogManager.getLogger(AgentFeelMustStarter.class);
    
    public static String ITEM_URL = "https://www.chanel.com/de/mode/p/AB6210B05630NC343/haarspange-metall-lammleder/";
    
    public final static String BRAND_GENDER = "women";

    public final static String BRAND_NAME_KOR = "샤넬";
    
    public final static String BRAND_NAME_ENG = "channel";

    //public final static String ITEM_CATEGORY = "t-shirt";
    public final static String ITEM_CATEGORY = "hair-accessoiry";

    //product info
    public final static String ITEM_TITLE_KOR = "헤어클립";
    public final static String ITEM_TITLE_ENG = "HAIR CLIP Metal Lambskin Gold black";
    public final static String ITEM_MODEL_NUMBER = "GKOOAB6653-B06123-ND152";
    public final static String ITEM_ORIGIN_COUNTRY= "유럽";
    public final static String ITEM_MATERIAL = "Metal & Lambskin, Gold & black";
    public final static String ITEM_COLOR = "골드";
    public final static String ITEM_SIZE_LIST = "one";
    public final static int    ITEM_PRICE_EURO = 420;
    public final static int    ITEM_DELIVERY_PRICE = 15000;
    
    //directory
    public final static String DIR_BRAND = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/의류/" + BRAND_NAME_ENG + "/";
    public final static String DIR_ITEM =  ITEM_CATEGORY + "/" + ITEM_TITLE_ENG;
    public final static String DIR_BRAND_ITEM = DIR_BRAND + DIR_ITEM;
    public final static String DIR_CAFE24_UPLOAD = "https://moondrive81.cafe24.com/GKoo/mode" + "/" + BRAND_NAME_ENG + "/" + ITEM_CATEGORY;
    
    public static void main(String[] args) throws Exception {
        ItemFeelway itemFeelway = getConfiguredItem();
        FeelwayController controller = new FeelwayController(itemFeelway);
        controller.createProductData();
    }
    
    private static List<String> getItemImageUrl() {
        List<String> itemImageUrl = new ArrayList<>();
        
        Document doc = null;
        try {
            doc = Jsoup.connect(ITEM_URL).userAgent("Chrome").get();
        } catch (IOException e) {
            
        }
        
        extractDetailImages(doc, itemImageUrl);
        return itemImageUrl;
    }
    
    public static void extractDetailImages(Document doc, List<String> itemImageUrl) {
        Element body = doc.body();
        Element detailsElement = body.getElementsByClass("product-details").get(0);
        Elements imageElements = detailsElement.getElementsByClass("carousel__outer").get(0).getElementsByTag("li");
        int size = imageElements.size();
        for (int i=0;i<size;i++) {
            //System.out.println(imageElements.get(i).getElementsByTag("img").get(1).attr("async-src"));
            itemImageUrl.add(imageElements.get(i).getElementsByTag("img").get(1).attr("async-src"));
        }
    }
    
    private static ItemFeelway getConfiguredItem() {
        ItemFeelway item = new ItemFeelway();
        item.setItemBrandKor(BRAND_NAME_KOR);
        item.setItemBrandEng(BRAND_NAME_ENG);
        item.setItemCategory(ITEM_CATEGORY);
        item.setItemBrandGender(BRAND_GENDER);
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
        item.setItemImageUrl(getItemImageUrl());
        return item;
    }
}
