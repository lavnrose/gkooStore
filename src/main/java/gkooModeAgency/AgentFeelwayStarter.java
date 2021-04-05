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
    public final static String ITEM_TITLE_KOR = "SS2021 코튼 피케 폴로 셔츠 화이트";
    public final static String ITEM_TITLE_ENG = "cotton pique polo shirt white";
    public final static String ITEM_MODEL_NUMBER = "0918A7060084556001";
    public final static String ITEM_ORIGIN_COUNTRY= "유럽";
    public final static String ITEM_MATERIAL = "100% 면";
    public final static String ITEM_COLOR = "화이트";
    public final static String ITEM_SIZE_LIST = "XS, S, M, L, XL";
    public final static int    ITEM_PRICE_EURO = 170;
    public final static int    ITEM_DELIVERY_PRICE = 10000;
    
    //directory
    public final static String DIR_BRAND = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/의류/" + BRAND_NAME_ENG + "/";
    public final static String DIR_ITEM =  BRAND_GENDER + "/" + ITEM_CATEGORY + "/" + ITEM_TITLE_ENG;
    public final static String DIR_BRAND_ITEM = DIR_BRAND + DIR_ITEM;
    public final static String DIR_CAFE24_UPLOAD = "https://moondrive81.cafe24.com/GKoo/mode" + "/" + BRAND_NAME_ENG + "/" + BRAND_GENDER + "/" + ITEM_CATEGORY;
    //https://moondrive81.cafe24.com/GKoo/mode/moncler/men/t-shirt/cotton_pique_polo_shirt_white_0.jpg
    
    public static void main(String[] args) throws Exception {
        ItemFeelway itemFeelway = getConfiguredItem();
        FeelwayController controller = new FeelwayController(itemFeelway);
        controller.createProductData();
    }
    
    private static List<String> getItemImageUrl() {
        //manual or crawler
        List<String> itemImageUrl = new ArrayList<>();
        itemImageUrl.add("https://cms.brnstc.de/product_images/435x596/cpro/media/images/product/21/2/100108986411000_0_1613404215526.jpg");
        itemImageUrl.add("https://cms.brnstc.de/product_images/435x596/cpro/media/images/product/21/2/100108986411000_1_1613117740735.jpg");
        itemImageUrl.add("https://cms.brnstc.de/product_images/435x596/cpro/media/images/product/21/2/100108986411000_3_1613117745175.jpg");
        itemImageUrl.add("https://cms.brnstc.de/product_images/435x596/cpro/media/images/product/21/2/100108986411000_6_1613117742690.jpg");
        
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
        item.setItemImageUrl(getItemImageUrl());
        return item;
    }
}
