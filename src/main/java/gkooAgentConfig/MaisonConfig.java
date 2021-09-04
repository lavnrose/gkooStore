package gkooAgentConfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;
import util.GrobalDefined.Gender2;

public final class MaisonConfig implements IAgentConfig {
    public final static String BRAND_HOMEPAGE_URL = "https://maisonkitsune.com/";
    public final static String BRAND_NAME_KOR = "메종 키츠네";
    public final static String BRAND_NAME_DE = "maison-kitsune";
    
    public final static String ROOT_DIR = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/의류/";
    //public final static String CATEGORY = "t-shirt";
    public final static String CATEGORY = "sweatshirts";
    //public static final String HTML_FILE_NAME = "men_t-shirt.html";
    public static final String HTML_FILE_NAME = "men_sweatshirts.html";
    
    public DirectoryLocationBuilder directoryLocationBuilder;
    
    public static final int DELIVERY_FEE= 10000;
    public static Gender2 GENDER = Gender2.MEN;
    
    public static final HashMap<String, String> CATEGORY_ID_SMARTSTORE_SET = new HashMap<>() {
        private static final long serialVersionUID = 1L;

        {
            put("남성의류_티셔츠", "50000830");
            put("남성의류_스웨터", "50000831");
        }
    };
    public static final String CATEGORY_ID_SMARTSTORE = CATEGORY_ID_SMARTSTORE_SET.get("남성의류_스웨터");
    
    public static final HashMap<String, String> CATEGORY_NUMBER_CAFE24_SET = new HashMap<>() {
        private static final long serialVersionUID = 1L;

        {
            put("남성의류_티셔츠", "300");
            put("남성의류_스웨터", "308");
        }
    };
    public static final String CATEGORY_NUMBER_CAFE24 = CATEGORY_NUMBER_CAFE24_SET.get("남성의류_스웨터");
    
    private static final String [] ITEM_SIZE_EU_WOMEN = {"XS", "S", "M", "L", "XL"};
    private static final String [] ITEM_SIZE_EU_MEN = {"XS", "S", "M", "L", "XL"};
    
    private static final String [] ITEM_SIZE_PRICE = {"0", "0", "0", "0", "0"};
    private static final String [] ITEM_SIZE_STOCK = {"1", "1", "1", "1", "1"};
    
    public static final List<String> ITEM_SIZE_LIST = Arrays.asList(ITEM_SIZE_EU_MEN);
    public static final List<String> ITEM_SIZE_PRICE_LIST = Arrays.asList(ITEM_SIZE_PRICE);
    public static final List<String> ITEM_SIZE_STOCK_LIST = Arrays.asList(ITEM_SIZE_STOCK);
    
    public MaisonConfig() {
        this.directoryLocationBuilder = 
                new DirectoryLocationBuilder(ROOT_DIR, BRAND_NAME_DE, GENDER, CATEGORY, HTML_FILE_NAME);
    }
    
    public DirectoryLocationBuilder getDirectoryLocationBuilder() {
        return directoryLocationBuilder;
    }
    
    public Supplier<String> getBrandNameCategory = () -> BRAND_NAME_KOR.concat(" ").concat(CATEGORY);

    @Override
    public String getBrandName() {
        return BRAND_NAME_DE;
    }

    @Override
    public String getBrandNameKor() {
        return BRAND_NAME_KOR;
    }

    @Override
    public String getHomepageUrl() {
        return BRAND_HOMEPAGE_URL;
    }

    @Override
    public String getMainImagesLocation() {
        return directoryLocationBuilder.getMainImagesLocation.get();
    }
    
    @Override
    public String getMainImagesCafe24Location() {
        return directoryLocationBuilder.getMainImagesCafe24Location.get();
    }

    @Override
    public List<String> getItemSizeList() {
        return ITEM_SIZE_LIST;
    }

    @Override
    public List<String> getItemStockList() {
        return ITEM_SIZE_STOCK_LIST;
    }

    @Override
    public List<String> getItemSizePriceList() {
        return ITEM_SIZE_PRICE_LIST;
    }

    @Override
    public int getDeliveryFee() {
        return DELIVERY_FEE;
    }

    @Override
    public String getCategoryIdSmartStore() {
        return CATEGORY_ID_SMARTSTORE;
    }
    
    @Override
    public String getCategoryIdCafe24() {
        return CATEGORY_NUMBER_CAFE24;
    }
}
