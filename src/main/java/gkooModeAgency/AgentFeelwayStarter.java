package gkooModeAgency;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import agencyBrandEntities.ItemFeelway;
import agencyController.FeelwayController;

public class AgentFeelwayStarter {
    private static final Logger LOGGER = LogManager.getLogger(AgentFeelwayStarter.class);
    
    //public final static String BRAND_NAME_KOR = "몽클레어";
    public final static String BRAND_NAME_KOR = "스톤아일랜드";
    
    //public final static String BRAND_NAME_ENG = "moncler";
    public final static String BRAND_NAME_ENG = "stone-island";
    public final static String BRAND_GENDER = "men";
    public final static String ITEM_CATEGORY = "t-shirt";
    
    //product info
    public final static String ITEM_TITLE_KOR = "SS2021 모노바나 티셔츠 올리브그린";
    public final static String ITEM_TITLE_ENG = "archivio monovana t-shirt";
    public final static String ITEM_MODEL_NUMBER = "0918A7060084556001";
    public final static String ITEM_ORIGIN_COUNTRY= "유럽";
    public final static String ITEM_MATERIAL = "100% 면";
    public final static String ITEM_COLOR = "올리브그린";
    public final static String ITEM_SIZE_LIST = "XS, S, M, L, XL";
    public final static int    ITEM_PRICE_EURO = 149;
    public final static int    ITEM_DELIVERY_PRICE = 10000;
    
    //directory
    public final static String DIR_BRAND = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/의류/" + BRAND_NAME_ENG + "/";
    public final static String DIR_ITEM =  BRAND_GENDER + "/" + ITEM_CATEGORY + "/" + ITEM_TITLE_ENG;
    public final static String DIR_BRAND_ITEM = DIR_BRAND + DIR_ITEM;
    public final static String DIR_CAFE24_UPLOAD = "https://moondrive81.cafe24.com/GKoo/mode" + "/" + BRAND_NAME_ENG + "/" + BRAND_GENDER + "/" + ITEM_CATEGORY;
    
    public static void main(String[] args) throws Exception {
        ItemFeelway itemFeelway = getConfiguredItem();
        FeelwayController controller = new FeelwayController(itemFeelway);
        controller.createProductData();
    }
    
    private static List<String> getItemImageUrl() {
        //manual or crawler
        List<String> itemImageUrl = new ArrayList<>();
        itemImageUrl.add("https://cdn.yoox.biz/12/12512766XG_13_f.jpg");
        itemImageUrl.add("https://cdn.yoox.biz/12/12512766XG_13_r.jpg");
        itemImageUrl.add("https://cdn.yoox.biz/12/12512766XG_13_d.jpg");
        itemImageUrl.add("https://cdn.yoox.biz/12/12512766XG_13_e.jpg");
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
        item.setItemImageUrl(getItemImageUrl());
        return item;
    }
}
