package gkooAgentConfig;

import java.util.List;

public interface IAgentConfig {
    String getBrandName();
    String getBrandNameKor();
    String getHomepageUrl();
    String getMainImagesLocation();
    List<String> getItemSizeList();
    List<String> getItemSizePriceList();
    List<String> getItemStockList();
    int getDeliveryFee();
    String getCategoryIdSmartStore();
    String getCategoryIdCafe24();
    String getMainImagesCafe24Location();
    String getCategory();
}
