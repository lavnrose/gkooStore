package agencyEntities;

import java.util.List;
import util.GrobalDefined.Gender;

public class Agent {
    public String brandNameDe;
    public String brandNameKor;
    public String homepageUrl;
    public String dirMainImages;
    List<String> itemSizeList;
    List<String> itemSizePriceList;
    List<String> itemSizeStockist;
    public Gender gender;
    public int deliveryFee;
    
    public Agent(String brandNameDe, String brandNameKor, String homepageUrl, String dirMainImages,
            List<String> itemSizeList, List<String> itemSizePriceList, List<String> itemSizeStockist, Gender gender, int deliveryFee) {
        this.brandNameDe = brandNameDe;
        this.brandNameKor = brandNameKor;
        this.homepageUrl = homepageUrl;
        this.dirMainImages = dirMainImages;
        this.itemSizeList = itemSizeList;
        this.itemSizePriceList = itemSizePriceList;
        this.itemSizeStockist = itemSizeStockist;
        this.gender = gender;
        this.deliveryFee = deliveryFee;
    }

    public String getBrandNameDe() {
        return brandNameDe;
    }

    public void setBrandNameDe(String brandNameDe) {
        this.brandNameDe = brandNameDe;
    }

    public String getBrandNameKor() {
        return brandNameKor;
    }

    public void setBrandNameKor(String brandNameKor) {
        this.brandNameKor = brandNameKor;
    }

    public String getHomepageUrl() {
        return homepageUrl;
    }

    public void setHomepageUrl(String homepageUrl) {
        this.homepageUrl = homepageUrl;
    }

    public String getDirMainImages() {
        return dirMainImages;
    }

    public void setDirMainImages(String dirMainImages) {
        this.dirMainImages = dirMainImages;
    }

    public List<String> getItemSizeList() {
        return itemSizeList;
    }

    public void setItemSizeList(List<String> itemSizeList) {
        this.itemSizeList = itemSizeList;
    }

    public List<String> getItemSizePriceList() {
        return itemSizePriceList;
    }

    public void setItemSizePriceList(List<String> itemSizePriceList) {
        this.itemSizePriceList = itemSizePriceList;
    }

    public List<String> getItemSizeStockist() {
        return itemSizeStockist;
    }

    public void setItemSizeStockist(List<String> itemSizeStockist) {
        this.itemSizeStockist = itemSizeStockist;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
}
