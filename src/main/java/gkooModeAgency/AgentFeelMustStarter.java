package gkooModeAgency;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import agencyBrandEntities.ItemFeelway;
import agencyController.FeelwayController;

// Feelway and MustIt
public class AgentFeelMustStarter {
    private static final Logger LOGGER = LogManager.getLogger(AgentFeelMustStarter.class);
    
    //public final static String BRAND_NAME_KOR = "몽클레어";
    //public final static String BRAND_NAME_KOR = "스톤아일랜드";
    public final static String BRAND_NAME_KOR = "휴고보스";
    
    //public final static String BRAND_NAME_ENG = "moncler";
    //public final static String BRAND_NAME_ENG = "stone-island";
    public final static String BRAND_NAME_ENG = "hugoboss";


    public final static String BRAND_GENDER = "men";

    //public final static String ITEM_CATEGORY = "t-shirt";
    public final static String ITEM_CATEGORY = "poloshirt";

    //product info
    public final static String ITEM_TITLE_KOR = "폴로셔츠 Paddy";
    public final static String ITEM_TITLE_ENG = "Polo-Shirt Paddy";
    public final static String ITEM_MODEL_NUMBER = "001251330";
    public final static String ITEM_ORIGIN_COUNTRY= "유럽";
    public final static String ITEM_MATERIAL = "100% 면";
    public final static String ITEM_COLOR = "블루";
    public final static String ITEM_SIZE_LIST = "S, M";
    public final static int    ITEM_PRICE_EURO = 56;
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
        itemImageUrl.add("https://outletcity.freetls.fastly.net/medias/sys_master/noidx/noidx/h2c/h40/9903225110558/4021409520926-bv-1280x1920-20.jpg?width=678");
        itemImageUrl.add("https://outletcity.freetls.fastly.net/medias/sys_master/noidx/noidx/ha8/h16/9903223668766/4021409520926-dt2-1280x1920-1.jpg?width=678%22");
        itemImageUrl.add("https://outletcity.freetls.fastly.net/medias/sys_master/noidx/noidx/he0/hc3/9903221538846/4021409520926-br-1280x1920-20.jpg?width=678");
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
