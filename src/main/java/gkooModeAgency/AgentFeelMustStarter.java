package gkooModeAgency;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import agencyBrandEntities.ItemFeelway;
import agencyController.FeelMustController;
import factoryExcel.SmartStore;

// Feelway and MustIt
public class AgentFeelMustStarter {
    private static final Logger LOGGER = LogManager.getLogger(AgentFeelMustStarter.class);
    
    //public final static String BRAND_NAME_KOR = "몽클레어";
    //public final static String BRAND_NAME_KOR = "스톤아일랜드";
    //public final static String BRAND_NAME_KOR = "휴고보스";
    //public final static String BRAND_NAME_KOR = "마쥬";
    public final static String BRAND_NAME_KOR = "메종 키츠네";
    
    //public final static String BRAND_NAME_ENG = "moncler";
    //public final static String BRAND_NAME_ENG = "stone-island";
    //public final static String BRAND_NAME_ENG = "hugoboss";
    public final static String BRAND_NAME_ENG = "maison-kitsune";


    public final static String BRAND_GENDER = "men";
    //public final static String BRAND_GENDER = "women";

    public final static String ITEM_CATEGORY = "t-shirt";
    //public final static String ITEM_CATEGORY = "poloshirt";

    //product info
    public final static String ITEM_TITLE_KOR = "쿨톤 폭스 패치 클래식 티셔츠";
    public final static String ITEM_TITLE_ENG = "COOL-TONE FOX HEAD PATCH CLASSIC TEE-SHIRT";
    public final static String ITEM_MODEL_NUMBER = "SKU HU00144KJ0008-P100";
    public final static String ITEM_ORIGIN_COUNTRY= "유럽";
    public final static String ITEM_MATERIAL = "100% cotton";
    public final static String ITEM_COLOR = "화이트";
    public final static String ITEM_SIZE_LIST = "S, M, L, XL";
    public final static int    ITEM_PRICE_EURO = 90;
    public final static int    ITEM_DELIVERY_PRICE = 10000;
    public final static boolean STOCK = true;
    public final static String CATEGORY_ID_SMARTSTORE = "50000830";
    
    //directory
    public final static String DIR_BRAND = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/의류/" + BRAND_NAME_ENG + "/";
    
    
    public final static String DIR_ITEM =  BRAND_GENDER + "/" + ITEM_CATEGORY + "/" + ITEM_TITLE_ENG;
    public final static String DIR_ITEM_STOCK =  "stock/" + ITEM_CATEGORY + "/" + ITEM_TITLE_ENG;
    
    public final static String DIR_BRAND_ITEM = STOCK ? DIR_BRAND + DIR_ITEM_STOCK : DIR_BRAND + DIR_ITEM;
    public final static String DIR_CAFE24_UPLOAD = "https://moondrive81.cafe24.com/GKoo/mode/" + BRAND_NAME_ENG + "/stock/" + ITEM_CATEGORY;
    
    public static void main(String[] args) throws Exception {
        ItemFeelway itemFeelway = getConfiguredItem();
        FeelMustController controller = new FeelMustController(itemFeelway);
        controller.createProductData(STOCK);

        //mode
        //smartstore
        //cafe24
        SmartStore smartStore = new SmartStore(controller, CATEGORY_ID_SMARTSTORE);
        smartStore.createExcelFeelMust(DIR_BRAND_ITEM);
        //cosmetic
        //coupang
        //cafe24
    }
    
    private static List<String> getItemImageUrl() {
        //manual or crawler with interface
        List<String> itemImageUrl = new ArrayList<>();
        //https://maisonkitsune.com/fr_en/cool-tone-fox-head-patch-classic-tee-shirt-white-60954910eb679.html
        itemImageUrl.add("https://media.maisonkitsune.com/media/catalog/product/cache/cf1b640b97181912f9e5c76e47031f2e/h/u/hu00144kj0008-p100_1.jpg");
        itemImageUrl.add("https://media.maisonkitsune.com/media/catalog/product/cache/cf1b640b97181912f9e5c76e47031f2e/h/u/hu00144kj0008-p100_2.jpg");
        itemImageUrl.add("https://media.maisonkitsune.com/media/catalog/product/cache/cf1b640b97181912f9e5c76e47031f2e/h/u/hu00144kj0008-p100_3.jpg");
        itemImageUrl.add("https://media.maisonkitsune.com/media/catalog/product/cache/cf1b640b97181912f9e5c76e47031f2e/h/u/hu00144kj0008-p100_4.jpg");
        itemImageUrl.add("https://media.maisonkitsune.com/media/catalog/product/cache/cf1b640b97181912f9e5c76e47031f2e/h/u/hu00144kj0008-p100_5.jpg");
        itemImageUrl.add("https://media.maisonkitsune.com/media/catalog/product/cache/cf1b640b97181912f9e5c76e47031f2e/h/u/hu00144kj0008-p100_6.jpg");
        
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
