package gkooModeAgency;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import agencyBrandEntities.ItemFeelway;
import agencyController.FeelwayController;

public class AgentFeelwayStarter {
    private static final Logger LOGGER = LogManager.getLogger(AgentFeelwayStarter.class);
    
    public final static String BRAND_NAME_KOR = "몽클레어";
    public final static String BRAND_NAME_ENG = "moncler";
    public final static String BRAND_GENDER = "men";
    public final static String ITEM_CATEGORY = "t-shirt";
    
    //product info
    public final static String ITEM_TITLE_KOR = "SS2021 트리 컬러 밴드 몽클레어 로고 저지 티셔츠";
    public final static String ITEM_TITLE_ENG = "The tricolour band Moncler logo jersey t-shirt";
    public final static String ITEM_MODEL_NUMBER = "0918C7B8108390T984";
    public final static String ITEM_MATERIAL = "100% 면";
    public final static String ITEM_COLOR = "화이트";
    public final static String ITEM_SIZE_LIST = "XS, S, M, L, XL";
    public final static int ITEM_PRICE_EURO = 185;
    public final static int ITEM_DELIVERY_PRICE = 10000;
    
    //directory
    public final static String DIR_BRAND = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/의류/" + BRAND_NAME_ENG + "/";
    public final static String DIR_ITEM =  BRAND_GENDER + "/" + ITEM_CATEGORY + "/" + ITEM_TITLE_ENG;
    public final static String DIR_BRAND_ITEM = DIR_BRAND + DIR_ITEM;
    public final static String HTML_BRAND = DIR_BRAND_ITEM + "/" + ITEM_TITLE_ENG + ".html";
    
    public static void main(String[] args) throws Exception {
        ItemFeelway itemFeelway = getConfiguredItem();
        FeelwayController controller = new FeelwayController(itemFeelway);
        controller.createProductData();
    }
    
    private static List<String> getItemImageUrl() {
        //manual or crawler
        List<String> itemImageUrl = new ArrayList<>();
        itemImageUrl.add("https://cdn.yoox.biz/12/12526989mb_13_f.jpg");
        itemImageUrl.add("https://cdn.yoox.biz/12/12526989mb_13_r.jpg");
        itemImageUrl.add("https://cdn.yoox.biz/12/12526989mb_13_d.jpg");
        
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
        item.setItemMaterial(ITEM_MATERIAL);
        item.setItemColor(ITEM_COLOR);
        item.setDirBrandItem(DIR_BRAND_ITEM);
        item.setItemPriceEuro(ITEM_PRICE_EURO);
        item.setItemSizeList(ITEM_SIZE_LIST);
        item.setItemImageUrl(getItemImageUrl());
        return item;
    }
    
}
