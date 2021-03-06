package agencyEntities;

import java.util.List;
import org.apache.commons.lang3.math.NumberUtils;
import gkooCosmeticAgency.AgentManual;
import util.GrobalDefined;
import util.GrobalDefined.Gender;

public class MassItem {
    /** common proeprties */
    private String itemUrl;
    private String brandHomepageUrl;
    private String brandNameDE;
    private String brandNameKor;
    private String itemCategory;
    private String categoryId;
    private String itemTitleKor;
    private String itemTitleDE;
    private String itemTitleEN;
    private Gender gender;
    private double itemPriceEuro;
    private double itemSalePriceEuro;
    private boolean isItemSale = false;
    private boolean isPriceVariable = false;

    //cafe24
    private double itemPriceWon;
    private double itemPriceWonCoupang;
    private String dirFileUploader;
    private List<String> itemDetailImages;
    private List<String> baseImages;
    
    /** mode proeprties */
    private List<String> itemColors;
    private List<String> itemSizes;
    private List<String> itemSizesPrice;
    private List<String> itemSizesStock;
    private List<String> itemOptions;
    private String materials;
    private int modeDeiveryFee;
    private String mainImageName;
    private String mainImageUrl;
    
    /** cosmetic proeprties */
    private String itemVolume;
    private String itemDescription; //de
    private String itemDescriptionKor;
    private String itemUsage;
    private boolean grobalUsage;
    private String itemIngredients;
    private int extraDeliveryFee;

    /**
     * Clothes
     * 
     * @param brandname
     * @param itemcategory
     * @param categoryid
     * @param gender
     */
    public MassItem(String brandname, String itemcategory, String categoryid, Gender gender) {
        this.brandNameDE = brandname;
        this.itemCategory = itemcategory;
        this.categoryId = categoryid;
        this.gender = gender;
    }
    
    public MassItem() {}
    
    /**
     * Cosmetics
     * 
     * @param itemTitle
     * @param categoryid
     * @param itemVolume
     * @param extraDeliveryFee
     * @param usage
     */
//    public MassItem(String itemTitle, String categoryid, String itemVolume, int extraDeliveryFee, String usage) {
//        this.brandNameDE =  AgentEco.BRAND_NAME_DE;
//        this.itemTitle = AgentEco.BRAND_NAME_KOR + " " + itemTitle;
//        this.categoryId = categoryid;
//        this.itemVolume = itemVolume;
//        this.extraDeliveryFee = extraDeliveryFee;
//        this.itemUsage = usage == null ? GrobalDefined.categoryUsage.get(categoryid).getUsage() : usage;
//    }
    
    /**
     * Cosmetics manual
     * 
     * @param itemTitle
     * @param itemVolume
     * @param extraDeliveryFee
     * @param usage
     */
    public MassItem(String itemTitleKor,String mainImageName, String itemVolume, int extraDeliveryFee, String usage, String dirFileUploader) {
        this.brandNameDE =  AgentManual.BRAND_NAME_DE;
        this.itemTitleKor = itemTitleKor;
        this.dirFileUploader = dirFileUploader;
        this.itemVolume = itemVolume;
        this.extraDeliveryFee = extraDeliveryFee;
        this.itemUsage = NumberUtils.isParsable(usage) ? GrobalDefined.categoryUsageManual.get(usage).getUsage() : usage;
        this.itemTitleDE = mainImageName;
        this.mainImageName = mainImageName;
    }
    
    public MassItem(String itemcategory) {
        this.itemCategory = itemcategory;
        this.grobalUsage = false; //default
    }
    
    public String getBrandNameDE() {
        return brandNameDE;
    }

    public void setBrandNameDE(String brandNameDE) {
        this.brandNameDE = brandNameDE;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemTitleKor() {
        return itemTitleKor;
    }

    public void setItemTitleKor(String itemTitleKor) {
        this.itemTitleKor = itemTitleKor;
    }

    public double getItemPriceEuro() {
        return itemPriceEuro;
    }

    public void setItemPriceEuro(double itemPriceEuro) {
        this.itemPriceEuro = itemPriceEuro;
    }

    public double getItemPriceWon() {
        return itemPriceWon;
    }

    public void setItemPriceWon(double itemPriceWon) {
        this.itemPriceWon = itemPriceWon;
    }

    public List<String> getItemDetailImages() {
        return itemDetailImages;
    }

    public void setItemDetailImages(List<String> itemDetailImages) {
        this.itemDetailImages = itemDetailImages;
    }

    public List<String> getItemColors() {
        return itemColors;
    }

    public void setItemColors(List<String> itemColors) {
        this.itemColors = itemColors;
    }

    public List<String> getItemSizes() {
        return itemSizes;
    }

    public void setItemSizes(List<String> itemSizes) {
        this.itemSizes = itemSizes;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getMainImageName() {
        return mainImageName;
    }

    public void setMainImageName(String mainImageName) {
        this.mainImageName = mainImageName;
    }
    
    public String getMainImageFileName() {
        return mainImageName + ".jpg";
    }
    
    public String toString() {
        StringBuilder str = new StringBuilder(); 
        str.append(""); 
        return str.toString();
    }

    public List<String> getBaseImages() {
        return baseImages;
    }

    public void setBaseImages(List<String> baseImages) {
        this.baseImages = baseImages;
    }

    public Gender getGender() {
        return gender;
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getItemVolume() {
        return itemVolume;
    }

    public void setItemVolume(String itemVolume) {
        this.itemVolume = itemVolume;
    }
    
    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemUsage() {
        return itemUsage;
    }

    public void setItemUsage(String itemUsage) {
        this.itemUsage = itemUsage;
    }

    public String getItemIngredients() {
        return itemIngredients;
    }

    public void setItemIngredients(String itemIngredients) {
        this.itemIngredients = itemIngredients;
    }

    public boolean isGrobalUsage() {
        return grobalUsage;
    }

    public void setGrobalUsage(boolean grobalUsage) {
        this.grobalUsage = grobalUsage;
    }

    public int getExtraDeliveryFee() {
        return extraDeliveryFee;
    }

    public void setExtraDeliveryFee(int extraDeliveryFess) {
        this.extraDeliveryFee = extraDeliveryFee;
    }

    public String getItemTitleDE() {
        return itemTitleDE;
    }

    public void setItemTitleDE(String itemTitleDE) {
        this.itemTitleDE = itemTitleDE;
    }

    public String getBrandNameKor() {
        return brandNameKor;
    }

    public void setBrandNameKor(String brandNameKor) {
        this.brandNameKor = brandNameKor;
    }

    public double getItemSalePriceEuro() {
        return itemSalePriceEuro;
    }

    public void setItemSalePriceEuro(double itemSalePriceEuro) {
        this.itemSalePriceEuro = itemSalePriceEuro;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public List<String> getItemSizesPrice() {
        return itemSizesPrice;
    }

    public void setItemSizesPrice(List<String> itemSizesPrice) {
        this.itemSizesPrice = itemSizesPrice;
    }

    public List<String> getItemSizesStock() {
        return itemSizesStock;
    }

    public void setItemSizesStock(List<String> itemSizesStock) {
        this.itemSizesStock = itemSizesStock;
    }

    public String getItemTitleEN() {
        return this.itemTitleEN;
    }

    public void setItemTitleEN(String itemTitleEN) {
        this.itemTitleEN = itemTitleEN;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public boolean isItemSale() {
        return isItemSale;
    }

    public void setItemSale(boolean isItemSale) {
        this.isItemSale = isItemSale;
    }

    public int getModeDeiveryFee() {
        return modeDeiveryFee;
    }

    public void setModeDeiveryFee(int modeDeiveryFee) {
        this.modeDeiveryFee = modeDeiveryFee;
    }

    public String getBrandHomepageUrl() {
        return brandHomepageUrl;
    }

    public void setBrandHomepageUrl(String brandHomepageUrl) {
        this.brandHomepageUrl = brandHomepageUrl;
    }

    public List<String> getItemOptions() {
        return itemOptions;
    }

    public void setItemOptions(List<String> itemOptions) {
        this.itemOptions = itemOptions;
    }

    public String getDirFileUploader() {
        return dirFileUploader;
    }

    public void setDirFileUploader(String dirFileUploader) {
        this.dirFileUploader = dirFileUploader;
    }

    public String getItemDescriptionKor() {
        return itemDescriptionKor;
    }

    public void setItemDescriptionKor(String itemDescriptionKor) {
        this.itemDescriptionKor = itemDescriptionKor;
    }

    public double getItemPriceWonCoupang() {
        return itemPriceWonCoupang;
    }

    public void setItemPriceWonCoupang(double itemPriceWonCoupang) {
        this.itemPriceWonCoupang = itemPriceWonCoupang;
    }

    public boolean isPriceVariable() {
        return isPriceVariable;
    }

    public void setPriceVariable(boolean isPriceVariable) {
        this.isPriceVariable = isPriceVariable;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }
}
